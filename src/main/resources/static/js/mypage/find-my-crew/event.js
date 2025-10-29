// navbar 이벤트
const navButtons = document.querySelectorAll(".nav-button");

navButtons.forEach((button) => {
    button.addEventListener("click", (e) => {
        navButtons.forEach((btn) => btn.classList.remove("active"));
        button.classList.add("active");
    });
});

// 여행 경로에서 경계선 주기
const color = [
    "#FF1744", // neon red (형광 빨강)
    "#FF9100", // neon orange (형광 주황)
    "#FFEA00", // neon yellow (형광 노랑)
    "#00E676", // neon green (형광 초록)
    "#2979FF", // neon blue (형광 파랑)
    "#304FFE", // neon navy (형광 남색 느낌)
    "#D500F9", // neon purple (형광 보라)
];
const tripPath = document.querySelectorAll(".trip-path");

tripPath.forEach((path) => {
    const markers = path.querySelectorAll(".marker");
    markers.forEach((marker, index) => {
        marker.firstElementChild.style.border = `1px solid ${
            color[index % color.length]
        }`;
    });
});

// 비행기 이동 애니메이션
const markers = document.querySelectorAll(".marker-circle.last");
let pos = -2;

setInterval(() => {
    pos += 1; // 1씩 증가
    if (pos > 90) {
        pos = -2;
        markers.forEach((marker) => {
            marker.style.transition = `left 0s linear`;
            marker.style.left = "-2%";
        });
    } else {
        markers.forEach((marker) => {
            marker.style.transition = `left 0.5s linear`;
            marker.style.left = pos + "%";
        });
    }
}, 100);
