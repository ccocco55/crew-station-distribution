// 제목 글자 수 카운트
const titleInput = document.querySelector(".title-input");
const titleCount = document.querySelector(".indicator");

if (titleInput && titleCount) {
    const updateCount = (e) => {
        titleCount.textContent = `${titleInput.value.length} / 60`;
    };
    titleInput.addEventListener("input", updateCount);
    updateCount();
}
