// 빈 값 유효성
const inputTags = document.querySelectorAll("input.form-control.essential");
console.log(inputTags);

inputTags.forEach((inputTag) => {
    inputTag.addEventListener("blur", (e) => {
        let error;
        if (inputTag.parentElement.parentElement.nextElementSibling) {
            error = inputTag.parentElement.parentElement.nextElementSibling;
        } else {
            error =
                inputTag.parentElement.parentElement.parentElement
                    .nextElementSibling;
        }
        if (inputTag.value.trim() === "") {
            inputTag.classList.add("error");

            error.style.display = "block";
            error.firstElementChild.textContent = "필수 입력 항목입니다.";
        } else {
            inputTag.classList.remove("error");
            error.style.display = "none";
        }
    });
});


// 비밀번호 확인 유효성

const passwordInput = document.querySelector(
    "input.form-control.essential.password"
);
const passwordCheckInput = document.querySelector(
    "input.form-control.essential.password-check"
);
const passwordError = document.querySelector("div.error-text-password");
const passwordCheckError = document.querySelector(
    "div.error-text-password-check"
);
console.log(passwordError);
console.log(passwordCheckError);

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
        // errorTags[1].firstElementChild.textContent = "필수 입력 항목입니다.";
    } else {
        passwordError.style.display = "none";
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
        // errorTags[1].firstElementChild.textContent = "필수 입력 항목입니다.";
    }
});
