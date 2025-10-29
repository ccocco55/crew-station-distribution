// ====== 크루/롱크루/숏크루 탭 필터 ======
const tabs = document.querySelectorAll(".report-tabs .tab-switch");
const rows = document.querySelectorAll("table.table-reports tbody tr");

const applyFilter = (type) => {
    // 탭 active 토글
    tabs.forEach((t) => t.classList.toggle("active", t.dataset.type === type));
    // 테이블 행 show/hide
    rows.forEach((r) => {
        r.style.display = r.dataset.type === type ? "" : "none";
    });
};

// 새로고침해도 유지
const getInitType = () =>
    new URLSearchParams(location.search).get("type") ||
    localStorage.getItem("reportType") ||
    "crew";

const setInitType = (type) => {
    localStorage.setItem("reportType", type);
    const p = new URLSearchParams(location.search);
    p.set("type", type);
    history.replaceState(null, "", `${location.pathname}?${p.toString()}`);
};

// 초기 적용
const initType = getInitType();
applyFilter(initType);

// 탭 클릭
tabs.forEach((tab) => {
    tab.addEventListener("click", () => {
        const type = tab.dataset.type;
        applyFilter(type);
        setInitType(type);
    });
});
