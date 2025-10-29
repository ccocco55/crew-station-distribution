// 카테고리 버튼 클릭 시 토글 이벤트
const categoryBtn = document.querySelectorAll(".category-button");

categoryBtn.forEach((btn) => {
    btn.addEventListener("click", (e) => {
        console.log(111111);

        categoryBtn.forEach((button) => {
            button.classList.remove("active");
        });

        btn.classList.add("active");
    });
});
