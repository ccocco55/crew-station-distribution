// ===================== My Sale Detail Event =====================
document.addEventListener("DOMContentLoaded", async () => {
    try {
        const pathParts = window.location.pathname.split("/");
        const paymentStatusId = pathParts[pathParts.length - 1];

        if (!paymentStatusId) {
            alert("잘못된 접근입니다. 결제 상태 ID가 없습니다.");
            return;
        }

        // 판매 상세 정보 불러오기
        const orderData = await fetchSaleDetail(paymentStatusId);
        if (!orderData) {
            alert("판매 상세 데이터를 불러오지 못했습니다.");
            return;
        }

        const container = document.querySelector(".main-wrap");
        if (!container) {
            console.error("렌더링할 컨테이너(.main-wrap)를 찾을 수 없습니다.");
            return;
        }

        saleLayout.renderSaleDetail(container, orderData);

    } catch (err) {
        console.error("판매 상세 페이지 렌더링 중 오류:", err);
        alert("판매 상세 페이지를 불러오는 중 문제가 발생했습니다.");
    }
});
