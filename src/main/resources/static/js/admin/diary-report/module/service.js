const diaryReportService = (() => {

    // 다이어리 신고 목록
    const getReports = async (page = 1) => {
        const res = await fetch(`/api/admin/diaries?page=${page}`);
        if (!res.ok) {
            console.error("신고 목록 로드 실패");
            return [];
        }
        return await res.json();
    };

    // 신고 처리
    const processReport = async (reportId, postId = null, hidePost = false) => {
        const params = new URLSearchParams();
        if (postId) params.append("postId", postId);
        if (hidePost) params.append("hidePost", hidePost);

        const res = await fetch(`/api/admin/diary/${reportId}/process?${params.toString()}`, {
            method: "POST"
        });

        if (!res.ok) {
            console.error("신고 처리 실패");
            return false;
        }

        return true;
    };

    return { getReports : getReports, processReport : processReport };
})();
