// 배너 슬라이드 부분
const banner = document.querySelector("div.banner-slide");
const firstBanner = document.createElement("div");
const lastBanner = document.createElement("div");
const arrows = document.querySelectorAll("div.arrow");
const bannerCount = document.querySelector("span.banner-count");

let count = 1;

banner.appendChild(firstBanner);
banner.prepend(lastBanner);

banner.style.transform = `translate(-1136px)`;

const autoSlide = () => {
    count++;
    banner.style.transform = `translate(-${1136 * count}px)`;
    banner.style.transition = `transform 0.5s`;

    if (count === 6) {
        setTimeout(() => {
            banner.style.transform = `translate(-1136px)`;
            banner.style.transition = `transform 0s`;
        }, 500);
        count = 1;
    }
    console.log(count);
    bannerCount.textContent = `${count} / 5`;
};

let autoSlideInterval = setInterval(autoSlide, 2000);
let arrowCheck = true;

arrows.forEach((arrow) => {
    const img = arrow.firstElementChild;
    // console.log("img", img);

    img.addEventListener("click", (e) => {
        if (!arrowCheck) {
            return;
        }
        console.log(img);

        arrowCheck = false;
        clearInterval(autoSlideInterval);

        const arrowType = img.classList[2];
        console.log("arrowType", arrowType);

        if (arrowType === "left") {
            console.log("왼쪽입니다.");

            count--;

            banner.style.transform = `translate(-${1136 * count}px)`;
            banner.style.transition = `transform 0.5s`;

            if (count === 0) {
                setTimeout(() => {
                    banner.style.transform = `translate(-5680px)`;
                    banner.style.transition = `transform 0s`;
                }, 500);
                count = 5;
            }
        } else {
            count++;
            console.log("오른쪽입니다.");

            banner.style.transform = `translate(-${1136 * count}px)`;
            banner.style.transition = `transform 0.5s`;
            if (count === 6) {
                setTimeout(() => {
                    banner.style.transform = `translate(-1136px)`;
                    banner.style.transition = `transform 0s`;
                }, 500);
                count = 1;
            }
        }

        autoSlideInterval = setInterval(autoSlide, 2000);
        setTimeout(() => {
            arrowCheck = true;
        }, 500);
        bannerCount.textContent = `${count} / 5`;
    });
});

// 크루 리스트 슬라이드 부분
// const crewList = document.querySelector(".crew-items");
// const crewRightBtn = document.querySelector(".crew-arrow-btn-wrap-right");
// const crewLeftBtn = document.querySelector(".crew-arrow-btn-wrap-left");
//
// console.log(crewLeftBtn);
// const displayBtn = (displayTag, noneTag) => {
//     displayTag.style.display = "block";
//     noneTag.style.display = "none";
// };
// crewRightBtn.addEventListener("click", (e) => {
//     crewList.style.transition = `transform 0.5s`;
//     crewList.style.transform = "translate(-1156px)";
//     displayBtn(crewLeftBtn, crewRightBtn);
//     // crewList.style.transition = `transform 0s`;
// });
//
// crewLeftBtn.addEventListener("click", (e) => {
//     crewList.style.transition = `transform 0.5s`;
//     crewList.style.transform = "translate(0px)";
//     displayBtn(crewRightBtn, crewLeftBtn);
//     // crewList.style.transition = `transform 0s`;
// });
//
// // 여행 경로에서 경계선 주기
// const color = [
//     "#FF1744", // neon red (형광 빨강)
//     "#FF9100", // neon orange (형광 주황)
//     "#FFEA00", // neon yellow (형광 노랑)
//     "#00E676", // neon green (형광 초록)
//     "#2979FF", // neon blue (형광 파랑)
//     "#304FFE", // neon navy (형광 남색 느낌)
//     "#D500F9", // neon purple (형광 보라)
// ];
// const tripPath = document.querySelectorAll(".trip-path");
//
// tripPath.forEach((path) => {
//     const markers = path.querySelectorAll(".marker");
//     markers.forEach((marker, index) => {
//         marker.firstElementChild.style.border = `1px solid ${
//             color[index % color.length]
//         }`;
//     });
// });
//
// // 비행기 이동 애니메이션
// const markers = document.querySelectorAll(".marker-circle.last");
// let pos = -2;
//
// setInterval(() => {
//     pos += 1; // 1씩 증가
//     if (pos > 90) {
//         pos = -2;
//         markers.forEach((marker) => {
//             marker.style.transition = `left 0s linear`;
//             marker.style.left = "-2%";
//         });
//     } else {
//         markers.forEach((marker) => {
//             marker.style.transition = `left 0.5s linear`;
//             marker.style.left = pos + "%";
//         });
//     }
// }, 100); // 0.1초마다 이동

// 타임 어택
function startCountdown() {
    const timers = document.querySelectorAll("div.gift-limit-time");

    timers.forEach((timer) => {
        const endTime = new Date(timer.dataset.endtime);

        function updateTimer() {
            const now = new Date();
            const diff = endTime - now;

            if (diff <= 0) {
                timer.textContent = "마감";
                return;
            }

            const totalSeconds = Math.floor(diff / 1000);

            const days = Math.floor(totalSeconds / (3600 * 24));
            const hours = String(
                Math.floor((totalSeconds % (3600 * 24)) / 3600)
            ).padStart(2, "0");
            const minutes = String(
                Math.floor((totalSeconds % 3600) / 60)
            ).padStart(2, "0");
            const seconds = String(totalSeconds % 60).padStart(2, "0");

            if (days > 0) {
                timer.textContent = `${days}일 ${hours}:${minutes}:${seconds} 남음`;
            } else {
                timer.textContent = `${hours}:${minutes}:${seconds} 남음`;
            }
        }

        updateTimer();
        setInterval(updateTimer, 1000);
    });
}

document.addEventListener("DOMContentLoaded", startCountdown);

// 좋아요 클릭 이벤트
const likeBtns = document.querySelectorAll("button.card-item-action-btn.like");
const toast = document.querySelector("div.toast");
const toastText = document.querySelector("p.toast-text");
let likeDoubleClick = true;

likeBtns.forEach((likeBtn) => {
    likeBtn.addEventListener("click", async () => {
        if (!likeDoubleClick) return;
        likeDoubleClick = false;

        const diaryId = likeBtn.dataset.diaryId;
        const svg = likeBtn.querySelector("svg");
        const countEl = likeBtn.querySelector(".count");
        let count = parseInt(countEl.textContent || "0", 10);

        const isActive = svg.classList.toggle("active");
        countEl.textContent = isActive ? count + 1 : Math.max(0, count - 1);

        try {
            if (isActive) {
                await likeService.addLike(diaryId);
                toastText.textContent = "좋아요가 추가되었습니다.";
            } else {
                await likeService.removeLike(diaryId);
                toastText.textContent = "좋아요가 취소되었습니다.";
            }
        } catch (err) {
            svg.classList.toggle("active", !isActive);
            countEl.textContent = count;
            toastText.textContent = "잠시 후 다시 시도해주세요.";
            console.error(err);
        }

        toast.style.display = "block";
        toast.classList.remove("hide");
        toast.classList.add("show");

        setTimeout(() => {
            toast.classList.remove("show");
            toast.classList.add("hide");
            setTimeout(() => {
                toast.style.display = "none";
                likeDoubleClick = true;
            }, 1000);
        }, 2000);
    });
});



