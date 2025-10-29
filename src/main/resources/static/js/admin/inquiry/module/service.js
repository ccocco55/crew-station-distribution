// ===== 문의 관리 Service =====
const inquireService = (() => {
    const BASE = '/api/admin/inquiries';

    const getList = async (opt = {}) => {
        const { keyword = '', category = '' } = opt || {};
        const qs = new URLSearchParams();
        if (keyword)  qs.set('keyword', keyword);
        if (category) qs.set('category', category);

        const res = await fetchWithRefresh(`${BASE}?${qs.toString()}`, {
            method: 'GET',
            credentials: 'include',
        });
        if (!res.ok) throw new Error('문의 목록 로드 실패');
        return await res.json();
    };

    const getDetail = async (id) => {
        const res = await fetchWithRefresh(`${BASE}/${encodeURIComponent(id)}`, {
            method: 'GET',
            credentials: 'include',
        });
        if (!res.ok) throw new Error('문의 상세 로드 실패');
        return await res.json(); // AskDTO
    };

    const postReply = async (id, replyContent) => {
        const res = await fetchWithRefresh(`${BASE}/${encodeURIComponent(id)}/reply`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify({ replyContent }),
        });
        if (!res.ok) throw new Error('답변 등록 실패');
        return true;
    };

    return { getList, getDetail, postReply };
})();
