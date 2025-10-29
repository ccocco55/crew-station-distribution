// 빈 값 체크, 비밀번호 + 비밀번호 확인 값 체크 유효성
const password = document.querySelector("input.password");
const passwordCheck = document.querySelector("input.password-check");
const errorTags = document.querySelectorAll("div.error-text-wrap");
const passwordBtn = document.querySelector("button.password-send-btn");
console.log(errorTags);

password.addEventListener("blur", (e) => {
    passwordBtn.disabled = true;
    if (password.value.trim() === "") {
        errorTags[0].style.display = "block";
        errorTags[0].firstElementChild.textContent = "필수 입력 항목입니다.";
    } else if (password.value.trim() !== passwordCheck.value.trim()) {
        errorTags[1].style.display = "block";
        errorTags[1].firstElementChild.textContent = "비밀번호가 다릅니다.";
    } else if (password.value.trim() === passwordCheck.value.trim()) {
        errorTags[1].style.display = "none";
        // errorTags[1].firstElementChild.textContent = "필수 입력 항목입니다.";
        passwordBtn.disabled = false;
    } else {
        errorTags[0].style.display = "none";
    }
});

passwordCheck.addEventListener("blur", (e) => {
    passwordBtn.disabled = true;
    if (passwordCheck.value.trim() === "") {
        errorTags[1].style.display = "block";
        errorTags[1].firstElementChild.textContent = "필수 입력 항목입니다.";
    } else if (password.value.trim() !== passwordCheck.value.trim()) {
        errorTags[1].style.display = "block";
        errorTags[1].firstElementChild.textContent = "비밀번호가 다릅니다.";
    } else if (password.value.trim() === passwordCheck.value.trim()) {
        errorTags[1].style.display = "none";
        // errorTags[1].firstElementChild.textContent = "필수 입력 항목입니다.";
        passwordBtn.disabled = false;
    }
});
