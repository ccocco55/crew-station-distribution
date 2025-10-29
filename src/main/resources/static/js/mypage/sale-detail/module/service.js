const fetchSaleDetail = async (paymentStatusId) => {
    try {
        const res = await fetch(`/api/mypage/sale-detail/${paymentStatusId}`);
        if (!res.ok) {
            throw new Error("판매 상세 정보를 불러오지 못했습니다.");
        }

        const text = await res.text();
        if (!text) {
            console.warn("서버 응답이 비어 있습니다 (204 No Content)");
            return null;
        }

        return JSON.parse(text);
    } catch (err) {
        console.error("fetchSaleDetail 오류:", err);
        alert("판매 상세 데이터를 불러오는 중 오류가 발생했습니다.");
    }
};