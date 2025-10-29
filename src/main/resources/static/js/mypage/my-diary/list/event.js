// navbar 이벤트
const navButtons = document.querySelectorAll(".nav-button");

navButtons.forEach((button) => {
    button.addEventListener("click", (e) => {
        navButtons.forEach((btn) => btn.classList.remove("active"));
        button.classList.add("active");
    });
});

// 편집 눌렀을 때

const updateButton = document.querySelector(".update-button");
const boxes = document.querySelectorAll(".checkbox");
const updateDiv = document.querySelector(".update-div");
const total = document.querySelector(".total");
const cancle = document.querySelector(".cancle");

updateButton.addEventListener("click", (e) => {
    boxes.forEach((box) => {
        box.style.display = "block";
    });

    updateDiv.style.display = "block";
    total.style.display = "block";
    updateButton.style.display = "none";
});

// 취소 눌렀을 때

cancle.addEventListener("click", (e) => {
    boxes.forEach((box) => {
        box.style.display = "none";
    });

    updateDiv.style.display = "none";
    total.style.display = "none";
    updateButton.style.display = "block";

    checkboxInputs.forEach((input) => {
        input.checked = false;
        count = 0;
        const wrapper = input.closest(".checkbox");
        wrapper.classList.remove("active");
        checkNumber.innerText = count;
    });
});

// 편집 체크박스

const checkboxInputs = document.querySelectorAll(".checkbox-input");
const checkNumber = document.querySelector(".check-number");
const remove = document.querySelector(".remove");
let count = 0;

checkboxInputs.forEach((input) => {
    input.addEventListener("change", (e) => {
        const wrapper = input.closest(".checkbox");
        if (input.checked) {
            wrapper.classList.add("active");
            count++;
        } else {
            wrapper.classList.remove("active");
            count--;
        }
        checkNumber.innerText = count;
        if (count >= 1) {
            remove.disabled = false;
            remove.classList.add("active");
        } else {
            remove.disabled = true;
            remove.classList.remove("active");
        }
    });
});

// 일기 개수

function updateTotal() {
    const totalNumber = document.querySelector(".total-number");
    const inputs = document.querySelectorAll(".checkbox-input");
    totalNumber.innerText = inputs.length;
}

updateTotal();

// 삭제

remove.addEventListener("click", (e) => {
    checkboxInputs.forEach((input) => {
        if (input.checked) {
            count = 0;
            const diaryWrap = input.closest(".diary-wrap");
            diaryWrap.remove();
            checkNumber.innerText = count;
        }
    });
    updateTotal();
});
