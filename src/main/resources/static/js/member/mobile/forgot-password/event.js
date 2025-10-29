// input 빈 값 유효성 검사
const inputTag = document.querySelector("input.member-email");
const inputWrap = document.querySelector("div.email-input-wrap");
const errorTag = document.querySelector("div.error-text-wrap");
const errorTextWrap = document.querySelector("div.error-text-wrap");

inputTag.addEventListener("blur", (e) => {
    if (inputTag.value.trim() === "") {
        inputWrap.classList.add("error");
        errorTag.style.display = "block";
        errorTextWrap.firstElementChild.textContent = "필수 입력 요소 입니다.";
    } else {
        inputWrap.classList.remove("error");
        errorTag.style.display = "none";
    }
});

// input에 값이 존재하면 확인 버튼 활성화
const mailCheckBtn = document.querySelector("button.mail-certification");
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

inputTag.addEventListener("input", (e) => {
    mailSendBtn.disabled = true;
    if (emailRegex.test(inputTag.value)) {
        inputWrap.classList.remove("error");
        mailCheckBtn.disabled = false;
        errorTag.style.display = "none";
    } else {
        mailCheckBtn.disabled = true;
        errorTag.style.display = "block";
        errorTextWrap.firstElementChild.textContent = "이메일 형식이 올바르지 않습니다.";
        inputWrap.classList.add("error");
    }
});

// 메일 존재 체크하고 이제 이메일로 인증코드 받기 확인하기
// 임시로 확인 부르면 바로 버튼 클릭 가능하게함 나중에 서버 맡은 분이 중복 / 존재 안함 시 관리해야됨
const mailSendBtn = document.querySelector("button.mail-send-btn");
// 메일 한 번 보내면 다시 못 보내게 체크하기
let mailSendCheck = true;
mailCheckBtn.addEventListener("click", async (e) => {
    // 여기서 서버 연결해서 응답에 따라 에러 문구가 나오거나 메일 인증 보내기 버튼 활성화
    let check = await checkService.checkEmail(inputTag.value);
    if (check) {
        mailSendBtn.disabled = false;
        inputWrap.classList.remove("error");
        errorTag.style.display = "none";
    } else {
        errorTextWrap.firstElementChild.textContent = "가입되지 않은 이메일 입니다.";
        inputWrap.classList.add("error");
        errorTag.style.display = "block";
    }
});

// mail 보내면 5분 동안(임시) 입력 가능하게 만들기
const codeInputWrap = document.querySelector("div.code-input-wrap");
const codeLimitTime = document.querySelector("div.limit-time");
const codeError = document.querySelector("div.mail-code");
const mailSendCheckBtn = document.querySelector("button.mail-send-check");
let time = 5 * 60;
let timer;
function updateTimer(timer) {
    const minutes = Math.floor(time / 60);
    const seconds = time % 60;
    document.querySelector(".limit-time").textContent = `${minutes}:${
        seconds < 10 ? "0" + seconds : seconds
    }`;

    if (time > 0) {
        time--;
    } else {
        codeError.style.display = "block";
        mailSendCheck = true;
        codeError.firstElementChild.textContent = "시간이 초과되었습니다.";
        clearInterval(timer); // 0초가 되면 멈춤
    }
}
// 메일 보내기

let serverCode = null;

mailSendBtn.addEventListener("click", (e) => {
    codeInputWrap.style.display = "block";
    mailSendCheckBtn.style.display = "block";
    mailSendBtn.style.display = "none"
});

mailSendBtn.addEventListener("click", async (e) => {
    serverCode = await checkService.sendEmail(inputTag.value);

    if (!mailSendCheck) return;
    mailSendCheck = false;;
    timer = setInterval(updateTimer, 1000);
    updateTimer(timer);

    console.log(serverCode)
});

// 메일 재전송
const mailResendBtn = document.querySelector("button.mail-resend");
mailResendBtn.addEventListener("click", async (e) => {
    // 시간
    serverCode = await checkService.sendEmail(inputTag.value);

    clearInterval(timer);
    time = 5 * 60;
    timer = setInterval(updateTimer, 1000);
    updateTimer(timer);

    console.log(serverCode)

//     이메일 전송
});

const codeInput = document.querySelector("input.code");
codeInput.addEventListener("blur", (e) => {
    if (codeInput.value.trim() === "") {
        codeInputWrap.classList.add("error");
        codeError.firstElementChild.textContent = "필수 입력 항목입니다.";
        codeError.style.display = "block";
        mailSendCheckBtn.disabled = true;
    } else {
        codeInputWrap.classList.remove("error");
        codeError.style.display = "none";
        mailSendCheckBtn.disabled = false;
    }
});

codeInput.addEventListener("input", (e) => {
    if (codeInput.value.trim() === "") {
        mailSendCheckBtn.disabled = true;
    } else {
        mailSendCheckBtn.disabled = false;
    }
});
const certify = document.querySelector(".certify");

// 인증번호 인증 버튼
mailSendCheckBtn.addEventListener("click", (e) => {
    e.preventDefault();
    // 인증 성공시
    if (serverCode.code  === codeInput.value) {
        clearInterval(timer);
        certify.style.display = "none";
        // 비밀번호 변경 layout으로 바꿔주기
        passwordLayout.passwordModal();
        // input에 이메일 값 넣어주기
        const hiddenEmailInput = document.querySelector(".email-member");

        hiddenEmailInput.value = inputTag.value;

        // 빈 값 체크, 비밀번호 + 비밀번호 확인 값 체크 유효성
        const passwordInput = document.querySelector(
            "input.member-password.password"
        );
        const passwordCheckInput = document.querySelector(
            "input.member-password.password-check"
        );
        const passwordError = document.querySelector("div.password-error-text-wrap");
        const passwordCheckError = document.querySelector(
            "div.error-text-wrap-check"
        );
        const passwordBtn = document.querySelector("button.password-send-btn");
        const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

        passwordInput.addEventListener("blur", (e) => {
            if (passwordInput.value.trim() === "") {
                passwordError.style.display = "block";
                passwordError.firstElementChild.textContent = "필수 입력 항목입니다.";
            } else if (passwordInput.value.trim() !== passwordCheckInput.value.trim()) {
                passwordCheckError.style.display = "block";
                passwordCheckError.firstElementChild.textContent =
                    "비밀번호가 다릅니다.";
            } else if (passwordInput.value.trim() === passwordCheckInput.value.trim()) {
                passwordCheckError.style.display = "none";
            } else {
                passwordError.style.display = "none";
            }
        });

        passwordInput.addEventListener("keyup", (e) => {
            const password = passwordInput.value.trim();

            if (!passwordRegex.test(password)) {
                passwordError.style.display = "block";
                passwordError.firstElementChild.textContent =
                    "영문, 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요.";

                passwordInput.classList.add("error");
            } else {
                passwordError.style.display = "none";
                passwordError.firstElementChild.textContent = "";

                passwordInput.classList.remove("error");
            }
        });

        passwordCheckInput.addEventListener("blur", (e) => {
            if (passwordCheckInput.value.trim() === "") {
                passwordCheckError.style.display = "block";
                passwordCheckError.firstElementChild.textContent =
                    "필수 입력 항목입니다.";
            } else if (passwordInput.value.trim() !== passwordCheckInput.value.trim()) {
                passwordCheckError.style.display = "block";
                passwordCheckError.firstElementChild.textContent =
                    "비밀번호가 다릅니다.";
            } else if (passwordInput.value.trim() === passwordCheckInput.value.trim()) {
                passwordCheckError.style.display = "none";
                passwordBtn.disabled = false;
            }
        });
    }
    // 실패시 에러 문구 보여주기
    else {
        codeInputWrap.classList.add("error");
        codeError.firstElementChild.textContent = "인증번호가 다릅니다.";
        codeError.style.display = "block";
    }
});
