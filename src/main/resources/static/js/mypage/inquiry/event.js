//  답변 전체보기

const answers = document.querySelectorAll(".answer");

answers.forEach((answer) => {
    const moreBtn = answer.querySelector(".more-button");
    const closeBtn = answer.querySelector(".close-button");

    if (moreBtn) {
        moreBtn.addEventListener("click", (e) => {
            answer.classList.add("active");
        });
    }

    if (closeBtn) {
        closeBtn.addEventListener("click", (e) => {
            answer.classList.remove("active");
        });
    }
});

// 문의 하기 삭제

const removeButtons = document.querySelectorAll(".remove");

removeButtons.forEach((btn) => {
    btn.addEventListener("click", (e) => {
        console.log("emfdjdha");

        const confirmed = confirm("정말 문의를 삭제하시겠어요?");
        if (confirmed) {
            const inquiry = btn.closest(".inquiry");
            inquiry.remove(); // inquiry 전체 묶음을 삭제
        }
    });
});
