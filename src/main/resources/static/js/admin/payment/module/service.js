const paymentService = (() => {
    const getPayments = async (page = 1, opt = {}) => {
        const { categories = [], category = "", keyword = "" } = opt || {};
        const qs = new URLSearchParams();
        qs.set("page", String(page));
        if (categories.length) qs.set("categories", categories.join(","));
        else if (category)    qs.set("category", category);
        if (keyword)          qs.set("keyword", keyword);

        const res = await fetchWithRefresh(`/api/admin/payment?${qs.toString()}`, {
            method: 'GET',
            credentials: 'include',
        });
        if (!res.ok) return [];
        return await res.json();
    };

    const getDetail = async (id) => {
        const res = await fetchWithRefresh(`/api/admin/payment/${encodeURIComponent(id)}`, {
            method: "GET",
            credentials: "include",

        });
        if (!res.ok) throw new Error("결제 상세 로드 실패");
        return await res.json();
    };

    const processPayment = async (paymentId, action = "cancel", body = {}) => {
        const res = await fetchWithRefresh(
            `/api/admin/payment/${encodeURIComponent(paymentId)}/${encodeURIComponent(action)}`,
            {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                credentials: "include",
                body: JSON.stringify(body),
            }
        );
        return res.ok;
    };

    const getSummary = async (opt = {}) => {
        const { categories = [], keyword = "" } = opt || {};
        const qs = new URLSearchParams();
        if (categories.length) qs.set("categories", categories.join(","));
        if (keyword) qs.set("keyword", keyword);

        const url = `/api/admin/payment/summary?${qs.toString()}`;

        const res = await fetchWithRefresh(url, { method: "GET", credentials: "include" });
        if (!res.ok) {
            throw new Error("결제 요약 불러오기 실패");
        }

        const data = await res.json();
        return data;
    };



    return { getPayments, getDetail, processPayment, getSummary };
})();
