const animationTags = document.querySelectorAll(
    "div.index-page-info-first div.slide-up"
);

const animationCrewTags = document.querySelectorAll(
    "div.index-page-info.crew div.slide-up.crew"
);

const animationDiaryTags = document.querySelectorAll(
    "div.index-page-info.diary div.slide-up.diary"
);

const animationGiftTags = document.querySelectorAll(
    "div.index-page-info.gift div.slide-up.gift"
);

const animationImgs = document.querySelectorAll("div.img-tag.slide-up");

const animationAction = (cnt) => {
    let tags = null;
    switch (cnt) {
        case 0:
            tags = animationTags;
            break;
        case 1:
            tags = animationCrewTags;
            break;
        case 2:
            tags = animationDiaryTags;
            break;
        case 3:
            tags = animationGiftTags;
            break;
        default:
            break;
    }
    tags.forEach((tag, index) => {
        let ms = 300;
        tag.classList.add("active");
        tag.style.animationDelay = `${ms * (index + 1)}ms`;
        tag.style.animationDuration = "500ms";
    });
    if (cnt !== 0) {
        const index = (cnt - 1) * 2;
        animationImgs[index].classList.add("active");
        animationImgs[index + 1].classList.add("active");
        animationImgs[index].style.animationDelay = "300ms";
        animationImgs[index].style.animationDuration = "500ms";
        animationImgs[index + 1].style.animationDelay = "600ms";
        animationImgs[index + 1].style.animationDuration = "500ms";
    }
};

animationTags.forEach((tag, index) => {
    tag.classList.add("active");

    index === 2
        ? (tag.style.animationDelay = "800ms")
        : (tag.style.animationDelay = "550ms");
    tag.style.animationDuration = "500ms";
});

const slideAnimation = document.querySelectorAll(
    "div.marquee-images-wrap-lists"
);

slideAnimation.forEach((tag, index) => {
    tag.style.animationDuration = "100s";
});

const height = window.innerHeight;
const navBarBtns = document.querySelectorAll("li.pagination-dolist-item");
let wheelCheck = true;
let currentY = 0;
let cnt = 0;
let navBarBtn = navBarBtns[0];
console.log(currentY % height);

const scroll = document.querySelector("div.scroll");
window.addEventListener("beforeunload", (e) => {
    document.querySelector(
        "main.index-page"
    ).style.transform = `translateY(0px)`;
    currentY = 0;
    window.scrollTo(0, 0);
});
window.addEventListener("wheel", (e) => {
    if (!wheelCheck) return;
    wheelCheck = false;
    if (e.wheelDeltaY < 0) {
        // 내려가는 부분

        console.log("내려갑니다");
        if (Math.abs(currentY) === height * 3) {
            console.log("그만 내려가");

            setTimeout(() => {
                wheelCheck = true;
            }, 1000);
            return;
        }
        cnt < 3 && animationAction(++cnt);
        currentY -= height;
        scroll.style.transform = `translateY(${currentY}px)`;
    } else {
        // 올라가는 부분
        console.log(cnt);

        console.log("올라갑니다");
        if (Math.abs(currentY) === 0) {
            console.log("그만 올라가");
            setTimeout(() => {
                wheelCheck = true;
            }, 1000);
            return;
        }
        animationAction(--cnt);
        console.log(detailBtns[cnt]);
        currentY += height;
        scroll.style.transform = `translateY(${currentY}px)`;
        console.log(currentY);
    }
    prevActive.classList.remove("active");
    detailBtns[cnt].classList.add("active");
    prevActive = detailBtns[cnt];
    console.log(currentY / height);
    navBarBtn.classList.remove("active");
    navBarBtns[(currentY / height) * -1].classList.add("active");
    navBarBtn = navBarBtns[(currentY / height) * -1];
    console.log(height);
    setTimeout(() => {
        wheelCheck = true;
    }, 1000);
});

const detailBtns = document.querySelectorAll("div.nav-detail-pagination-btn");
let prevActive = document.querySelector("div.nav-detail-pagination-btn.active");
let btnCheck = true;
detailBtns.forEach((detailBtn, index) => {
    detailBtn.addEventListener("click", (e) => {
        if (!btnCheck) return;
        btnCheck = false;
        navBarBtn.classList.remove("active");
        prevActive.classList.remove("active");
        detailBtn.classList.add("active");
        navBarBtn = navBarBtns[index];
        navBarBtns[index].classList.add("active");
        prevActive = detailBtn;
        animationAction(index);
        cnt = index;
        currentY = index * height * -1;
        scroll.style.transform = `translateY(-${height * index}px)`;
        setTimeout(() => {
            btnCheck = true;
        }, 1000);
    });
});
