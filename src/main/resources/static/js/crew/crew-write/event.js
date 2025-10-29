// 이미지 첨부

const pickFile = () => {
    const input = document.createElement("input");
    input.type = "file";
    input.accept = "image/*";
    input.hidden = true;
    document.body.appendChild(input);
    return input;
};

// 프로필 기본값 (멘션 이미지)
const DEFAULT_PROFILE_IMG = "/static/images/crew-station-icon-profile.png";

// 블록 내부의 모든 멘션 핀/카드 초기화
const clearMentions = (root) => {
    if (!root) return;
    root.querySelectorAll(".img-tag-container").forEach((pin) => {
        // 위치/상태/아이디 초기화
        pin.hidden = true;
        pin.style.left = "";
        pin.style.top = "";
        delete pin.dataset.ready;
        delete pin.dataset.pinId;

        // 카드(mention-window) 닫기
        const win = pin.querySelector(".mention-window");
        if (win) {
            win.style.display = "none";
            win.setAttribute("hidden", "");
        }

        // 프로필/이름 기본값으로
        const mentionImg = pin.querySelector(".mention-profile-img img");
        const mentionName = pin.querySelector("#mention-name");
        if (mentionImg) mentionImg.src = DEFAULT_PROFILE_IMG;
        if (mentionName) mentionName.textContent = "CREW2";
    });
};

const resetBlock = (block) => {
    if (!block) return;
    block.dataset.idx = "";

    // 이미지/툴바(삭제,다시 올리기) 초기화
    const existedImg = block.querySelector(".img-container img");
    if (existedImg) existedImg.remove();

    const dz = block.querySelector(".dropzone");
    if (dz) dz.hidden = false;

    const bottom = block.querySelector(".post-img-bottom");
    if (bottom) bottom.hidden = true;

    // 버튼/텍스트 초기화
    const btn = block.querySelector(".edit-button");
    if (btn) btn.textContent = "+ 맨션 태그 추가";
    const ta = block.querySelector(".post-input");
    if (ta) ta.value = "";

    // 편집 상태/좌표 초기화
    block.dataset.armed = "0";
    block.dataset.tx = "";
    block.dataset.ty = "";

    // 멘션 핀/카드까지 전부 초기화
    clearMentions(block);
};

const previewIn = (block, url) => {
    if (!block) return;
    const box = block.querySelector(".img-container");
    if (!box) return;

    let img = box.querySelector("img");
    if (!img) {
        img = document.createElement("img");
        img.className = "css-n9shyu";
        box.appendChild(img);
    }
    img.src = url;

    const dz = block.querySelector(".dropzone");
    if (dz) dz.hidden = true;
    const bottom = block.querySelector(".post-img-bottom");
    if (bottom) bottom.hidden = false;
};

const leftList = document.querySelector(".left-img-add-container");
const contentList = document.querySelector(".post-img-content-container");
const thumbTpl = document.querySelector("#thumb-tpl");
const sampleBlock = document.querySelector(".post-img-content-wrapper");
const tagModal = document.querySelector(".modal-popout");
const closeModalBtn = document.querySelector(".close-btn");
const pagePlusBtn = document.querySelector(".sub-img-plus-btn-container");
const completeBtn = document.querySelector(".complete-btn");

let currentBlock = null;
// 모달로 선택 진행 중인 핀 id
let activePinId = "";

/*****************
 * 드롭다운 (나이대)
 *****************/
const allDrops = document.querySelectorAll(".write-content-select-dropdown");
document.addEventListener("click", (e) => {
    const selectInput = e.target.closest(".write-content-select");
    if (selectInput) {
        const wrap = selectInput.closest(".write-content-select-wrap");
        const dropdown = wrap?.querySelector(".write-content-select-dropdown");

        if (dropdown) {
            allDrops.forEach(
                (d) => d !== dropdown && (d.style.display = "none")
            );
            dropdown.style.display =
                dropdown.style.display === "block" ? "none" : "block";
        }
        return;
    }

    // 옵션 클릭 → 값 반영 후 닫기
    const li = e.target.closest(".write-content-select-dropdown li");
    if (li) {
        const dropdown = li.closest(".write-content-select-dropdown");
        const wrap = dropdown.closest(".write-content-select-wrap");
        const input = wrap.querySelector(".write-content-select");
        input.value = li.textContent.trim();
        dropdown.style.display = "none";
        return;
    }

    // 바깥 클릭 → 모두 닫기
    if (!e.target.closest(".write-content-select-wrap")) {
        allDrops.forEach((d) => (d.style.display = "none"));
    }
});

document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") {
        document
            .querySelectorAll(".write-content-select-dropdown")
            .forEach((d) => (d.style.display = "none"));
        if (tagModal) tagModal.style.display = "none";
    }
});

/*****************
 * 시작 상태 정리
 *****************/
document
    .querySelectorAll(".edit-button")
    .forEach((b) => (b.textContent = "+ 맨션 태그 추가"));

if (leftList) {
    const plusItems = leftList.querySelectorAll(".post-sub-img.add");
    for (let i = 1; i < plusItems.length; i++) plusItems[i].remove();
}

/*****************
 * 인덱스 관리
 *****************/
let nextIdx = 0;
const freeIdx = [];
const takeIdx = () => (freeIdx.length ? freeIdx.shift() : nextIdx++);
const giveIdx = (i) => {
    const pos = freeIdx.findIndex((v) => v > i);
    if (pos === -1) freeIdx.push(i);
    else freeIdx.splice(pos, 0, i);
};

/*****************
 * 정렬용 위치 탐색
 *****************/
const findThumbBefore = (idx) => {
    if (!leftList) return null;
    const list = leftList.querySelectorAll(".post-sub-img:not(.add)");
    for (const li of list) if (+li.dataset.idx > idx) return li;
    return leftList.querySelector(".post-sub-img.add");
};
const findBlockBefore = (idx) => {
    if (!contentList) return null;
    const list = contentList.querySelectorAll(
        ".post-img-content-wrapper[data-idx]"
    );
    for (const li of list) if (+li.dataset.idx > idx) return li;
    return null;
};

/*****************
 * 파일 1개 → 썸네일 + 본문 블록 추가
 *****************/
const addPairWithFile = (file) => {
    if (!file || !file.type?.startsWith("image/")) return;
    if (!contentList || !leftList || !thumbTpl || !sampleBlock) return;

    const url = URL.createObjectURL(file);
    const idx = takeIdx();

    let block;
    const firstIsEmpty =
        sampleBlock &&
        !sampleBlock.dataset.idx &&
        !sampleBlock.querySelector(".img-container img");
    if (firstIsEmpty && idx === 0) {
        block = sampleBlock;
    } else {
        block = sampleBlock.cloneNode(true);
        resetBlock(block);
    }
    block.dataset.idx = String(idx);
    previewIn(block, url);

    const beforeBlk = findBlockBefore(idx);
    beforeBlk
        ? contentList.insertBefore(block, beforeBlk)
        : contentList.appendChild(block);

    // 왼쪽 썸네일
    const frag = document.importNode(thumbTpl.content, true);
    const thumb = frag.firstElementChild;
    thumb.dataset.idx = String(idx);
    const imgView = thumb.querySelector(".img-view");
    if (imgView) imgView.src = url;

    const beforeThumb = findThumbBefore(idx);
    leftList.insertBefore(thumb, beforeThumb);
};

/*****************
 * 왼쪽 썸네일 영역
 *****************/
leftList?.addEventListener("click", (e) => {
    // 좌측 + 버튼 → 파일 선택
    if (e.target.closest(".sub-img-plus-btn-container")) {
        const input = pickFile();
        input.onchange = () => {
            const f = input.files?.[0];
            if (f) addPairWithFile(f);
            input.remove();
        };
        input.click();
        return;
    }

    // 썸네일 삭제
    const delBtn = e.target.closest(".delete-sub-img");
    if (delBtn) {
        const li = delBtn.closest(".post-sub-img");
        const idx = +li.dataset.idx;
        li.remove();

        const blk = contentList?.querySelector(
            `.post-img-content-wrapper[data-idx="${idx}"]`
        );
        if (currentBlock === blk) currentBlock = null; // 참조 정리
        blk?.remove();
        giveIdx(idx);

        if (!leftList.querySelector(".post-sub-img:not(.add)")) {
            if (sampleBlock && !sampleBlock.isConnected)
                contentList?.appendChild(sampleBlock);
            resetBlock(sampleBlock);
        }
        return;
    }

    // 썸네일 클릭 → 해당 블록으로 스크롤
    const thumb = e.target.closest(".post-sub-img:not(.add)");
    if (thumb && !e.target.closest(".delete-sub-img")) {
        const block = contentList?.querySelector(
            `.post-img-content-wrapper[data-idx="${thumb.dataset.idx}"]`
        );
        block?.scrollIntoView({ behavior: "smooth", block: "center" });
    }
});

/*****************
 * 게시글 하단 큰 + 버튼 → "빈 블록" 추가 (이미지 선택 X)
 *****************/
pagePlusBtn?.addEventListener("click", () => {
    if (!contentList || !sampleBlock) return;

    const idx = takeIdx();
    let block;

    const firstIsEmpty =
        sampleBlock &&
        !sampleBlock.dataset.idx &&
        !sampleBlock.querySelector(".img-container img");
    if (firstIsEmpty && idx === 0) {
        block = sampleBlock;
        resetBlock(block);
    } else {
        block = sampleBlock.cloneNode(true);
        resetBlock(block);
    }
    block.dataset.idx = String(idx);

    const beforeBlk = findBlockBefore(idx);
    beforeBlk
        ? contentList.insertBefore(block, beforeBlk)
        : contentList.appendChild(block);

    // 빈 블록이므로 썸네일은 업로드 시 생성
});

/*****************
 * 오른쪽 본문 영역
 *****************/
contentList?.addEventListener("click", (e) => {
    const block = e.target.closest(".post-img-content-wrapper");
    if (!block) return;

    // 업로드 / 다시 올리기
    if (e.target.closest(".pc-upload-btn") || e.target.closest(".return-img")) {
        const input = pickFile();
        input.onchange = () => {
            const f = input.files?.[0];
            if (!f?.type?.startsWith("image/")) return input.remove();

            const url = URL.createObjectURL(f);
            previewIn(block, url);

            if (!block.dataset.idx) {
                const i = takeIdx();
                block.dataset.idx = String(i);

                const frag = document.importNode(thumbTpl.content, true);
                const thumb = frag.firstElementChild;
                thumb.dataset.idx = String(i);
                const imgView = thumb.querySelector(".img-view");
                if (imgView) imgView.src = url;

                const beforeThumb = findThumbBefore(i);
                leftList?.insertBefore(thumb, beforeThumb);
            } else {
                const iv = leftList?.querySelector(
                    `.post-sub-img[data-idx="${block.dataset.idx}"] .img-view`
                );
                if (iv) iv.src = url;
            }
            input.remove();
        };
        input.click();
        return;
    }

    // 삭제(오른쪽)
    if (e.target.closest(".delete-img")) {
        const i = +block.dataset.idx;
        if (currentBlock === block) currentBlock = null; // 참조 정리
        leftList?.querySelector(`.post-sub-img[data-idx="${i}"]`)?.remove();
        block.remove();
        giveIdx(i);

        if (!leftList?.querySelector(".post-sub-img:not(.add)")) {
            if (sampleBlock && !sampleBlock.isConnected)
                contentList?.appendChild(sampleBlock);
            resetBlock(sampleBlock);
        }
        return;
    }

    // 태그 편집 토글
    if (e.target.closest(".edit-button")) {
        const btn = e.target.closest(".edit-button");
        const armed = block.dataset.armed === "1";
        block.dataset.armed = armed ? "0" : "1";
        btn.textContent = armed ? "+ 맨션 태그 추가" : "맨션 편집 완료";
        return;
    }

    // 이미지 클릭(편집 중) → 좌표 저장 + 모달
    const box = e.target.closest(".img-add-container");
    if (box && box.querySelector("img") && block.dataset.armed === "1") {
        const r = box.getBoundingClientRect();
        const relY = (e.clientY - r.top) / r.height;
        if (relY > 0.85) return; // 하단 툴바 영역 무시

        const xPct = ((e.clientX - r.left) / r.width) * 100;
        const yPct = ((e.clientY - r.top) / r.height) * 100;
        block.dataset.tx = xPct.toFixed(2);
        block.dataset.ty = yPct.toFixed(2);

        currentBlock = block;
        activePinId = ""; // 좌표로 찍는 경우는 특정 핀 미지정

        if (tagModal) {
            tagModal.style.display = "block";
            tagModal.style.position = "absolute";
            tagModal.style.left = `${e.pageX}px`;
            tagModal.style.top = `${e.pageY + 12}px`;
            tagModal.style.transform = "translate(-50%,0)";
        }
        return;
    }

    // 작은 + 버튼 클릭
    if (e.target.closest(".tag-add-btn")) {
        const pin = e.target.closest(".img-tag-container");
        if (pin) {
            currentBlock = block;

            // 고유 id 부여(없으면)
            if (!pin.dataset.pinId) {
                pin.dataset.pinId = String(Date.now() + Math.random());
            }

            // 아직 멘션 선택 전이면 모달로 선택 유도
            if (pin.dataset.ready !== "1") {
                activePinId = pin.dataset.pinId;

                const b = e.target
                    .closest(".tag-add-btn")
                    .getBoundingClientRect();
                if (tagModal) {
                    tagModal.style.display = "block";
                    tagModal.style.position = "absolute";
                    tagModal.style.left = `${
                        b.left + b.width / 2 + window.scrollX
                    }px`;
                    tagModal.style.top = `${b.bottom + window.scrollY}px`;
                    tagModal.style.transform = "translate(-50%,0)";
                }
            } else {
                // 이미 선택된 핀 → 카드 토글만
                const win = pin.querySelector(".mention-window");
                if (win) {
                    const show = win.style.display !== "block";
                    win.style.display = show ? "block" : "none";
                    if (show) win.removeAttribute("hidden");
                }
            }
        }
        return;
    }

    // 태그 핀 클릭(카드 밖 영역) → 핀 숨기기
    const pin = e.target.closest(".img-tag-container");
    if (pin && !pin.hidden && !e.target.closest(".tag-add-btn")) {
        pin.hidden = true;
    }
});

/*****************
 * 태그 모달 선택/닫기
 *****************/
closeModalBtn?.addEventListener("click", () => {
    if (tagModal) tagModal.style.display = "none";
});

tagModal?.addEventListener("click", (e) => {
    // 바깥 클릭 → 닫기
    if (!e.target.closest(".modal-view")) {
        tagModal.style.display = "none";
        return;
    }

    // "선택" 클릭
    if (e.target.closest(".tag-select-btn") && currentBlock) {
        // 1) 대상 핀 찾기: activePinId가 있으면 그 핀, 없으면 숨겨진 핀 하나
        let pin = null;
        if (activePinId) {
            pin = currentBlock.querySelector(
                `.img-tag-container[data-pin-id="${activePinId}"]`
            );
        }
        if (!pin)
            pin = currentBlock.querySelector(".img-tag-container[hidden]");

        if (!pin) {
            alert("태그는 최대 3개까지만 추가할 수 있어요!");
            tagModal.style.display = "none";
            activePinId = "";
            return;
        }

        // 2) 프로필 데이터 주입
        const profileRow = e.target.closest(".tag-profile-container");
        const profileImg =
            profileRow?.querySelector(".ymDK1")?.src ||
            "/static/images/crew-station-icon-profile.png";
        const profileName =
            profileRow?.querySelector(".member-name")?.innerText || "CREW";

        const win = pin.querySelector(".mention-window");
        const imgEl = win?.querySelector(".mention-profile-img img");
        const nameEl = win?.querySelector("#mention-name");
        if (imgEl) imgEl.src = profileImg;
        if (nameEl) nameEl.innerText = profileName;

        // 3) 좌표 지정(이미지 클릭으로 저장된 경우)
        if (currentBlock.dataset.tx && currentBlock.dataset.ty) {
            pin.style.left = `${currentBlock.dataset.tx}%`;
            pin.style.top = `${currentBlock.dataset.ty}%`;
        }

        // 4) 상태 업데이트: 핀 보여주고, 카드는 기본 닫힘
        pin.hidden = false;
        pin.dataset.ready = "1";
        if (win) {
            win.style.display = "none";
            win.setAttribute("hidden", "");
        }

        // 5) 종료
        activePinId = "";
        tagModal.style.display = "none";
    }
});

/*****************
 * 상단 ‘여행 경로 태그’ 칩 생성
 *****************/
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
        chip.addEventListener("click", () => chip.remove());
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

/*****************
 * 작성 버튼(트리거만)
 *****************/
completeBtn?.addEventListener("click", () => {
    console.log("작성 클릭");
});
