// 일기 공개/비공개 설정

const secretCheckbox = document.querySelector(".secret-checkbox");
const secretToggle = document.querySelector(".secret-toggle");
const lockIcon = document.querySelector(".lock");
const unlockIcon = document.querySelector(".unlock");

secretToggle.addEventListener("click", (e) => {
    secretCheckbox.checked = secretToggle.classList.toggle("active");
    if (secretCheckbox.checked) {
        lockIcon.classList.remove("hidden");
        unlockIcon.classList.add("hidden");
    } else {
        lockIcon.classList.add("hidden");
        unlockIcon.classList.remove("hidden");
    }
});

// 이미지 팝업

const targets = document.querySelectorAll(".target");
const images = document.querySelectorAll(".diary-img");
const modal = document.querySelector(".img-modal");
const modalImg = document.querySelector(".img-modal img");
const closeBtn = document.querySelector(".img-close-button");

targets.forEach((target, i) => {
    target.addEventListener("click", () => {
        const imgSrc = images[i].getAttribute("src");
        modalImg.setAttribute("src", imgSrc);
        modal.style.display = "flex";
    });
});

// 닫기 버튼
closeBtn.addEventListener("click", () => {
    modal.style.display = "none";
});

// 모달 바깥 클릭하면 닫기
modal.addEventListener("click", (e) => {
    if (e.target === modal) {
        modal.style.display = "none";
    }
});

// 신고하기 버튼

const reportModal = document.querySelector(".report-modal");
const replyReporyBtns = document.querySelectorAll(".detail-report-button");

replyReporyBtns.forEach((replyReporyBtn) => {
    replyReporyBtn.addEventListener("click", (e) => {
        reportModal.classList.add("active");
    });
});

// 신고하기 창 끄기

const reportCloseBtn = document.querySelector(".close-button");

reportCloseBtn.addEventListener("click", (e) => {
    reportModal.classList.remove("active");
    selectFirstReportRadio();
});

// 신고하기 창 신고 종류 고르기

const reportOptions = document.querySelectorAll(".report-content");
const radioBtns = document.querySelectorAll(".radio-button");

reportOptions.forEach((reportOption) => {
    reportOption.addEventListener("click", (e) => {
        radioBtns.forEach((radioBtn) => {
            radioBtn.classList.remove("active");
            const btn = radioBtn.querySelector(".radio");
            btn.checked = false;
        });

        const btn = reportOption.querySelector(".radio");

        btn.checked = true;
        const radioButton = reportOption.querySelector(".radio-button");
        radioButton.classList.add("active");
    });
});

// 신고하기 창 제출 버튼

const submitReportBtn = document.querySelector(".report-button-send");

submitReportBtn.addEventListener("click", (e) => {
    reportModal.classList.remove("active");
    selectFirstReportRadio();
});

// 신고하기 창 제출/끄기 후 첫번째 라디오 버튼 선택
function selectFirstReportRadio() {
    const radioBtns = document.querySelectorAll(".radio-button");
    radioBtns.forEach((radioBtn, idx) => {
        const btn = radioBtn.querySelector(".radio");
        if (idx === 0) {
            radioBtn.classList.add("active");
            if (btn) btn.checked = true;
        } else {
            radioBtn.classList.remove("active");
            if (btn) btn.checked = false;
        }
    });
}

// 댓글 입력 버튼 활성화/비활성화

const input = document.querySelector(".input");
const enterButton = document.querySelector(".enter-button");

input.addEventListener("input", (e) => {
    if (e.target.value.length > 0) {
        enterButton.classList.add("active");
        enterButton.disabled = false;
    } else {
        enterButton.classList.remove("active");
        enterButton.disabled = true;
    }
});

// 댓글 입력 버튼 클릭 시 입력 비활성화

enterButton.addEventListener("click", (e) => {
    input.value = "";
    enterButton.classList.remove("active");
    enterButton.disabled = true;
});

// 댓글 수정

document
    .querySelectorAll(".modify-reply-button")
    .forEach((modifyReplyButton) => {
        modifyReplyButton.addEventListener("click", () => {
            const replyContent = modifyReplyButton.closest(".reply-content");
            const replyFlexBox = replyContent.querySelector(
                "div[style='display: flex;']"
            );
            const replyTextDiv = replyFlexBox.querySelector(
                ".reply-content-text"
            );
            const originalText = replyTextDiv.textContent;

            const input = document.createElement("input");

            input.type = "text";
            input.className = "reply-edit-input";
            input.value = originalText;

            // 저장 버튼 만들기
            const saveButton = document.createElement("button");
            saveButton.textContent = "저장";
            saveButton.className = "save-reply-button";

            // 기존 텍스트 제거하고 input, 버튼 만들기
            replyFlexBox.innerHTML = "";
            replyFlexBox.appendChild(input);
            replyFlexBox.appendChild(saveButton);

            // 다시 텍스트로
            saveButton.addEventListener("click", () => {
                const newText = input.value;

                // 새로운 텍스트 div 생성
                const newTextDiv = document.createElement("div");
                newTextDiv.className = "reply-content-text";
                newTextDiv.textContent = newText;

                // input과 버튼 제거하고 텍스트 복원
                replyFlexBox.innerHTML = "";
                replyFlexBox.appendChild(newTextDiv);
            });
        });
    });

// 댓글 삭제

const replyRemoveButtons = document.querySelectorAll(".remove-reply-button");
const removeModal = document.querySelector(".remove-modal");
const removeNoButton = removeModal.querySelector(".remove-no");
const removeOkButton = removeModal.querySelector(".remove-ok");

let targetReply = null; // 삭제 대상 reply-item 저장용

replyRemoveButtons.forEach((btn) => {
    btn.addEventListener("click", (e) => {
        removeModal.style.display = "block";
        targetReply = btn.closest(".reply-item"); // 삭제할 대상 저장
    });
});

// 취소 버튼 클릭 시 모달 닫기
removeNoButton.addEventListener("click", () => {
    removeModal.style.display = "none";
    targetReply = null;
});

// 확인 버튼 클릭 시 reply-item 삭제 후 모달 닫기
removeOkButton.addEventListener("click", () => {
    if (targetReply) {
        targetReply.remove();
        targetReply = null;
    }
    removeModal.style.display = "none";
});

// 일기 삭제

const diaryRemoveButton = document.querySelector(".remove-button");
const diaryRemoveModal = document.querySelector(".diary-remove-modal");
const NoButton = diaryRemoveModal.querySelector(".remove-no");
const OkButton = diaryRemoveModal.querySelector(".remove-ok");

let targetPost = null; // 삭제 대상 reply-item 저장용

diaryRemoveButton.addEventListener("click", (e) => {
    diaryRemoveModal.style.display = "block";
    targetReply = btn.closest(".reply-item"); // 삭제할 대상 저장
});

// 취소 버튼 클릭 시 모달 닫기
NoButton.addEventListener("click", () => {
    diaryRemoveModal.style.display = "none";
    targetReply = null;
});

// 페이지 클릭
const numberButtons = document.querySelectorAll(".number-button");
const prevButton = document.querySelector(".prev-button");
const nextButton = document.querySelector(".next-button");

let currentPage = 1;
const totalPages = numberButtons.length;

// 페이지 표시 갱신 함수
function updatePagination() {
    numberButtons.forEach((btn, index) => {
        btn.classList.toggle("active", index + 1 === currentPage);
    });

    // prev 버튼 처리
    if (currentPage === 1) {
        prevButton.classList.remove("active");
        prevButton.disabled = true;
    } else {
        prevButton.classList.add("active");
        prevButton.disabled = false;
    }

    // next 버튼 처리
    if (currentPage === totalPages) {
        nextButton.disabled = true;
        nextButton.classList.remove("active");
    } else {
        nextButton.disabled = false;
        nextButton.classList.add("active");
    }
}

// 숫자 버튼 클릭 이벤트
numberButtons.forEach((numberButton, index) => {
    numberButton.addEventListener("click", () => {
        currentPage = index + 1;
        updatePagination();
    });
});

// 이전 버튼
prevButton.addEventListener("click", () => {
    if (currentPage > 1) {
        currentPage--;
        updatePagination();
    }
});

// 다음 버튼
nextButton.addEventListener("click", () => {
    if (currentPage < totalPages) {
        currentPage++;
        updatePagination();
    }
});

// 초기 상태 세팅
updatePagination();

// 게시물 좋아요 버튼

const likeButton = document.querySelector(".sticky-like-button");

likeButton.addEventListener("click", (e) => {
    const likeIcon = likeButton.querySelector(".like-before");
    likeIcon.classList.toggle("active");
});

// 댓글로가기 버튼

const goToCommentButton = document.querySelector(".go-reply");

goToCommentButton.addEventListener("click", (e) => {
    const commentSection = document.querySelector(".reply-container");
    commentSection.scrollIntoView({ behavior: "smooth" });
});

// 공유하기 버튼 클릭 이벤트

const shareButton = document.querySelector(".sticky-share-button");
const toast = document.querySelector(".toast");

shareButton.addEventListener("click", (e) => {
    toast.style.display = "block";
    toast.classList.remove("hide");
    toast.classList.add("show");
    setTimeout(() => {
        toast.classList.remove("show");
        toast.classList.add("hide");
        setTimeout(() => {
            toast.style.display = "none";
        }, 500);
    }, 3000);
    clip();
});

function clip() {
    var url = "";
    var textarea = document.createElement("textarea");
    document.body.appendChild(textarea);
    url = window.location.href; // 현재 URL을 가져옵니다.
    textarea.value = url;
    textarea.select(); // 텍스트 영역의 내용을 선택합니다.
    document.execCommand("copy"); // 선택된 내용을 클립보드에 복사합니다.
    document.body.removeChild(textarea); // 텍스트 영역을 제거합니다.
}
