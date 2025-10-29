const noticeService = (() => {

    // 공지 목록 조회
    const getList = async (callback, page = 1) => {
        try {
            const res = await fetch(`/api/admin/notices?page=${page}`);
            const data = await res.json();

            if (res.ok) {
                console.log("공지 목록 불러오기 성공");
                callback?.(data);
            } else {
                console.error("공지 목록 실패:", data);
            }
        } catch (err) {
            console.error("서버 통신 오류:", err);
        }
    };

    // 공지 작성
    // noticeService.save
    const save = async (title, content) => {
        try {
            const res = await fetch("/api/admin/notices", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ title, content }),
                credentials: "include", // 쿠키(JWT) 쓰면 붙이는 게 안전
            });

            // 디버깅 로그
            console.log("[NOTICE SAVE] status:", res.status);

            // 성공 범위를 넓게: 200, 201, 204 모두 성공 처리
            if (res.status === 200 || res.status === 201 || res.status === 204) {
                return true;
            }

            // 혹시 302 같은 리다이렉트가 오면 여기로
            const text = await res.text();
            console.error("공지 작성 실패 응답:", res.status, text);
            return false;
        } catch (err) {
            console.error("서버 오류:", err);
            return false;
        }
    };


    //  공지 상세 조회
    const getDetail = async (id) => {
        try {
            const res = await fetch(`/api/admin/notices/${id}`);
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.json();
            console.log("공지 상세 불러오기 성공:", data);
            return data;
        } catch (err) {
            console.error("공지 상세 불러오기 실패:", err);
            throw err;
        }
    };

    return { getList, save, getDetail };
})();
