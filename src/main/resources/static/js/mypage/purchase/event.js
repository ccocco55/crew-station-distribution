document.addEventListener("DOMContentLoaded", () => {
    const searchBtn = document.getElementById("searchBtn");
    const searchInput = document.getElementById("keywordInput");

    if (!searchBtn || !searchInput) {
        console.warn("검색창 요소를 찾을 수 없습니다.");
        return;
    }

    function goSearch() {
        const keyword = searchInput.value.trim();
        const baseUrl = "/mypage/purchase-list";
        const params = new URLSearchParams();

        if (keyword) params.append("keyword", keyword);
        params.append("page", 1);
        params.append("size", 10);

        window.location.href = `${baseUrl}?${params.toString()}`;
    }

    // 버튼 클릭 시 검색
    searchBtn.addEventListener("click", goSearch);

    // 엔터 키로 검색
    searchInput.addEventListener("keydown", (e) => {
        if (e.key === "Enter") {
            goSearch();
        }
    });
});
