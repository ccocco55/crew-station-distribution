// 제목 글자 수 카운트

(() => {
    const titleInput = document.getElementById("crewTitle");
    const titleCount = document.getElementById("titleCount");
    if (!titleInput || !titleCount) return;

    const max = titleInput.maxLength > 0 ? titleInput.maxLength : 20;
    const update = () =>
        (titleCount.textContent = `${titleInput.value.length} / ${max}`);
    titleInput.addEventListener("input", update);
    update();
})();

// 본문 글자 수 카운트 (textarea)
(() => {
    const contentIpt = document.getElementById("postContent");
    const contentCount = document.getElementById("contentCount");
    if (!contentIpt || !contentCount) return;

    const max = contentIpt.maxLength > 0 ? contentIpt.maxLength : 100;
    const update = () =>
        (contentCount.textContent = `${contentIpt.value.length} / ${max}`);
    contentIpt.addEventListener("input", update);
    update();
})();

// 만들기 버튼
const completeBtn = document.querySelector(".complete-btn");
if (completeBtn) {
    completeBtn.addEventListener("click", () => {
        console.log("만들기 클릭");
    });
}

// 사진 첨부 버튼 클릭 이벤트
(() => {
    const trigger = document.querySelector(".cover-img-border");
    if (!trigger) return;

    const file = document.createElement("input");
    file.type = "file";
    file.accept = "image/*";
    file.hidden = true;
    document.body.appendChild(file);

    let currentURL = null;

    trigger.addEventListener("click", () => file.click());

    file.addEventListener("change", () => {
        const f = file.files?.[0];
        if (!f) return;

        if (currentURL) {
            URL.revokeObjectURL(currentURL);
            currentURL = null;
        }
        currentURL = URL.createObjectURL(f);

        trigger.style.backgroundImage = `url("${currentURL}")`;
        trigger.style.backgroundSize = "cover";
        trigger.style.backgroundPosition = "center";

        const guide = trigger.querySelector(".img-btn-wrapper");
        if (guide) guide.style.display = "none";
    });

    window.addEventListener("beforeunload", () => {
        if (currentURL) URL.revokeObjectURL(currentURL);
    });
})();

// 상단 태그 입력(칩 생성)
(() => {
    const form = document.querySelector(".input-tag-container");
    if (!form) return;

    const input = form.querySelector(".input-tag-wrap");
    const btn = form.querySelector(".input-tag-btn");
    let list = form.parentElement.querySelector(".tag-list");

    if (!list) {
        list = document.createElement("div");
        list.className = "tag-list";
        form.parentElement.appendChild(list);
    }

    const addChip = () => {
        const v = (input.value || "").trim();
        if (!v) return;
        const chip = document.createElement("button");
        chip.type = "button";
        chip.className = "tag-chip";
        chip.textContent = v;

        chip.addEventListener("click", (e) => chip.remove());

        list.appendChild(chip);
        input.value = "";
    };

    form.addEventListener("submit", (e) => {
        e.preventDefault();
        addChip();
    });
    btn?.addEventListener("click", (e) => {
        e.preventDefault();
        addChip();
    });

    input?.addEventListener("keydown", (e) => {
        if (e.key === "Enter" && !e.isComposing) {
            e.preventDefault();
            addChip();
        }
    });
})();
