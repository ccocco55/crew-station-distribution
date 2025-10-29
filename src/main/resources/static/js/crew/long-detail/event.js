// 신고하기 버튼

const reportBtn = document.querySelector(".report-button");
const reportModal = document.querySelector(".report-modal");
const replyReporyBtn = document.querySelector(".reply-report-button");

replyReporyBtn.addEventListener("click", (e) => {
    reportModal.classList.add("active");
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

// 링크 주소 복사 및 토스트 메시지

const shareButton = document.querySelector(".menu-share-container");
const toast = document.querySelector(".toast");

shareButton.addEventListener("click", (e) => {
    function clip() {
        console.log("클립보드 복사");
        var url = "";
        var textarea = document.createElement("textarea");
        document.body.appendChild(textarea);
        url = window.location.href;
        textarea.value = url;
        textarea.select();
        document.execCommand("copy");
        document.body.removeChild(textarea);

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
    }
    clip();
});

// 이미지 슬라이드
const container = document.querySelector(".post-img-container");
const track = container.querySelector(".post-imgs");
const slides = container.querySelectorAll(".post-img");
const prevBtn = container.querySelector(".arrow-btn.left");
const nextBtn = container.querySelector(".arrow-btn.right");
const current = container.querySelector("#current");
const total = container.querySelector("#total");

// 처음 이미지 인덱스 번호
let index = 0;
// 마지막 이미지 인덱스 번호
const lastIndex = slides.length - 1;

// 총 이미지 개수 표시
total.textContent = slides.length;

// 보여줄 이미지 바꾸기
function showSlide(i) {
    // 범위 제한
    index = Math.max(0, Math.min(i, lastIndex));

    // 이미지 묶음을 왼쪽으로 이동
    track.style.transform = `translateX(-${index * 100}%)`;

    // 현재 번호 갱신
    current.textContent = index + 1;
}

// 버튼에 이벤트 달기
prevBtn.addEventListener("click", () => showSlide(index - 1));
nextBtn.addEventListener("click", () => showSlide(index + 1));

// 시작 시 첫 번째 이미지 보이기
showSlide(0);

// 신청 버튼 클릭 이벤트
const topBtn = document.querySelector(".new-crew-open");

topBtn.addEventListener("click", (e) => {
    console.log(1111);
});
