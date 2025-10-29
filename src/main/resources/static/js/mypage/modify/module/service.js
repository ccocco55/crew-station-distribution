const mypageService = {
    async getMemberInfo() {
        const response = await fetch("/api/mypage/modify/info", {
            method: "GET",
            headers: { "Content-Type": "application/json" },
        });

        if (!response.ok) {
            throw new Error("회원 정보를 불러오지 못했습니다.");
        }

        return await response.json();
    },
};