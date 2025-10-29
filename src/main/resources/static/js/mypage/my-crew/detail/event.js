// 공유하기 버튼 클릭 이벤트

const shareButton = document.querySelector(".share-button");
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

// navbar 이벤트
const navButtons = document.querySelectorAll(".nav-button");
const crewMembers = document.querySelectorAll(".crew-member");

navButtons.forEach((button, i) => {
    button.addEventListener("click", (e) => {
        navButtons.forEach((btn) => btn.classList.remove("active"));
        button.classList.add("active");

        crewMembers.forEach((crew, j) => {
            if (i === j) {
                crew.classList.add("active");
            } else {
                crew.classList.remove("active");
            }
        });
        member(i);
        noEdit();
    });
});

// 편집 눌렀을 때

const updateButton = document.querySelector(".update-button");
const boxes = document.querySelectorAll(".checkbox");
const updateDiv = document.querySelector(".update-div");
const total = document.querySelector(".total");
const cancle = document.querySelector(".cancle");
const checks = document.querySelectorAll(".check");

updateButton.addEventListener("click", (e) => {
    boxes.forEach((box) => {
        box.style.display = "block";
    });

    updateDiv.style.display = "block";
    total.style.display = "block";
    updateButton.style.display = "none";

    checks.forEach((check) => (check.style.display = "block"));
});

// 취소 눌렀을 때
function noEdit() {
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
    checks.forEach((check) => (check.style.display = "none"));
}
cancle.addEventListener("click", (e) => {
    noEdit();
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

// 삭제

remove.addEventListener("click", (e) => {
    checkboxInputs.forEach((input) => {
        if (input.checked) {
            count = 0;
            const profile = input.closest(".crew-profile");
            profile.remove();
            checkNumber.innerText = count;
        }
    });
    updateTotal(crewProfiles);
});

// 전체개수
const crewProfiles = document.querySelectorAll(".crew-profile");
const navFooter = document.querySelector(".nav-footer");

function member(i) {
    if (i === 1) {
        updateTotal();
        navFooter.style.display = "flex";
    } else {
        navFooter.style.display = "none";
    }
}

member(1);

// 다이어리 좋아요

const likeButtons = document.querySelectorAll(".card-item-action-btn");

likeButtons.forEach((btn) => {
    btn.addEventListener("click", (e) => {
        btn.classList.toggle("active");
    });
});

// 요청 거절

const refuses = document.querySelectorAll(".refuse");

refuses.forEach((refuse) => {
    refuse.addEventListener("click", (e) => {
        confirm("승인을 거절 하시겠습니까?");
        if (confirm) {
            refuse.closest("a").remove();
        } else {
            return;
        }
    });
});

// 요청 승인

const applies = document.querySelectorAll(".apply");

applies.forEach((apply) => {
    apply.addEventListener("click", (e) => {
        confirm("승인 하시겠습니까?");
        if (confirm) {
            refuse.closest("a").remove();
        } else {
            return;
        }
    });
});
