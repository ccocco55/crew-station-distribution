// 체크박스 작동 표시
const autoCheckbox = document.getElementById("auto-checkbox");
const checkIcon = document.getElementById("check-icon");

autoCheckbox.addEventListener("change", (e) => {
    checkIcon.classList.toggle("active", e.target.checked);
});

// 로그인 버튼 클릭 이벤트
const loginbutton = document.getElementById("login-button");
const emailinput = document.getElementById("email-input");
const passwordinput = document.getElementById("password-input");

const emailmodal = document.getElementById("email-test");
const passwordmodal = document.getElementById("password-test");
const errormodal = document.getElementById("password-error-test");

// 로그인 페이지 진입 시 rememberEmail 쿠키로 자동 채움
(function fillRememberedEmail() {
    const m = document.cookie.match(/(?:^|;\s*)rememberEmail=([^;]+)/);
    if (!m) return;
    const email = decodeURIComponent(m[1]);
    if (emailinput) emailinput.value = email;
    if (autoCheckbox) {
        autoCheckbox.checked = true;
        checkIcon?.classList.toggle("active", true);
    }
})();

// 모달 닫기 버튼 기본 링크 방지
document
    .querySelectorAll(
        "#boot-alert-success-email, #boot-alert-success-password, #boot-alert-success-error"
    )
    .forEach((a) =>
        a.addEventListener("click", (e) => {
            e.preventDefault();
            e.target.closest(".boot-alert").parentElement.style.display = "none";
        })
    );

// 로그인 버튼 클릭 시
loginbutton.addEventListener("click", async (e) => {
    e.preventDefault();

    const email = emailinput.value.trim();
    const password = passwordinput.value.trim();
    const remember = !!autoCheckbox.checked;

    // 이메일 형식 검사
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        emailmodal.style.display = "inline";
        return;
    }

    // 비밀번호 입력 확인
    if (!password) {
        passwordmodal.style.display = "inline";
        return;
    }

    try {
        const member = { memberEmail: email, memberPassword: password, remember };
        const result = await memberService.login(member);
        console.log("로그인 성공:", result);
        window.location.href = "/admin";
    } catch (error) {
        console.error("로그인 실패:", error);
        errormodal.style.display = "inline";
    }
});


