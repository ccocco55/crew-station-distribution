// 버튼 클릭 이벤트 더보기 (주) 버킷플레이스부분
const moreBtn = document.querySelector("button.footer-more-btn");
const arrow = document.querySelector("button.footer-more-btn >span");
const moreMune = document.querySelector("div.more-modal-layout");
moreBtn.addEventListener("click", (e) => {
    console.log(123);

    if (arrow.classList.contains("active")) {
        arrow.classList.remove("active");
        moreMune.classList.remove("active");
    } else {
        arrow.classList.add("active");
        moreMune.classList.add("active");
    }
});
