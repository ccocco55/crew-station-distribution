// ===================== Order Layout =====================
const orderLayout = (() => {

    // 상태 매핑
    const mapStatusText = (status) => {
        switch (status) {
            case "request":
                return "수락 대기";
            case "pending":
                return "결제 대기";
            case "success":
                return "결제 완료";
            case "received":
                return "수령 완료";
            case "reviewed":
                return "리뷰 완료";
            case "refund":
                return "주문 취소";
            default:
                return status || "";
        }
    };

    // 날짜 + 상태 라벨 처리
    function getPaymentStatusLabel(order) {
        let baseDate = null;

        switch (order.paymentStatus) {
            case "request":
                baseDate = order.createdDatetime;
                break;
            case "pending":
            case "success":
            case "received":
            case "reviewed":
            case "refund":
                baseDate = order.updatedDatetime;
                break;
            default:
                return "";
        }

        // 날짜 포맷: "MM/DD(DY)"
        const dateObj = new Date(baseDate);
        const month = String(dateObj.getMonth() + 1).padStart(2, "0");
        const day = String(dateObj.getDate()).padStart(2, "0");

        // 요일 매핑
        const week = ["일", "월", "화", "수", "목", "금", "토"];
        const dayLabel = week[dateObj.getDay()];

        return `${month}/${day}(${dayLabel})`;
    }

    function renderStatusLines(status) {
        // 상태값 → 순서 매핑
        const statusOrder = {
            request: 0,   // 수락 대기
            pending: 1,   // 결제 대기
            success: 2,   // 결제 완료
            received: 3,  // 수령 완료
            reviewed: 4   // 리뷰 완료
        };

        const labels = ["수락 대기", "결제 대기", "결제 완료", "수령 완료", "리뷰 완료"];
        const currentIndex = statusOrder[status] ?? 0;

        return labels.map((label, idx) => {
            if (idx < currentIndex) {
                return `
                <div class="line fill">
                    <h3 class="status">${label}</h3>
                </div>
            `;
            } else if (idx === currentIndex) {
                return `
                <div class="line">
                    <span class="now">
                        <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" fill="none">
                            <path fill="#2F3438" fill-rule="evenodd" d="M1.201 3.182a.6.6 0 0 1 .847.05l3.996 4.495 3.997-4.496a.6.6 0 1 1 .896.798l-4.07 4.58a1.1 1.1 0 0 1-1.645 0l-4.07-4.58a.6.6 0 0 1 .05-.847" clip-rule="evenodd"></path>
                        </svg>
                    </span>
                    <h3 class="status">${label}</h3>
                </div>
            `;
            } else {
                return `
                <div class="line">
                    <h3 class="status">${label}</h3>
                </div>
            `;
            }
        }).join("");
    }

    // 전화번호 포맷
    function formatPhone(phone) {
        if (!phone) return "";
        const digits = phone.replace(/\D/g, "");
        if (digits.length === 10) {
            return digits.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3");
        } else if (digits.length === 11) {
            return digits.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
        }
        return phone;
    }

    // 배송 방법 포맷
    function formatDelivery(delivery) {
        if(delivery === "direct") {
            return "직접 전달";
        }
        if(delivery === "parcel") {
            return "택배 거래";
        }
        return "알 수 없음";
    }

    // 상세 정보 렌더링
    const renderOrderDetail = (container, order) => {
        container.innerHTML = `
        <div class="main-title">
            주문번호 <span class="title-section">I</span>
            <span class="order-number">${order.guestOrderNumber}</span>
        </div>
        <section class="header ${order.paymentStatus === "refund" ? "refund-bg" : ""}">
            <section class="header-wrapper">
                <h1 class="header-title">
                    <span class="date">${getPaymentStatusLabel(order) || ""}</span> 
                    <span>${mapStatusText(order.paymentStatus)}</span>
                </h1>
            </section>
            <div class="status-wrapper">
                ${renderStatusLines(order.paymentStatus)}
            
                <button class="status-btn cancel-btn ${order.paymentStatus === "request"  ? "active" : ""}">주문 취소</button>
                <button class="status-btn payment-btn ${order.paymentStatus === "pending" ? "active" : ""}">결제하기</button>
                <button class="status-btn receive-btn ${order.paymentStatus === "success" ? "active" : ""}">수령 완료</button>
                <button class="status-btn review-btn ${order.paymentStatus === "received" ? "active" : ""}">
                    <img class="manner-icon" src="/images/manner.png"> 별점 주기
                </button>
            </div>
        </section>

        <hr class="bar">

        <div class="product">
            <section class="header-wrapper">
                <h1 class="header-title"><span>결제 정보</span></h1>
            </section>
            
            <a href="/gifts/detail/${order.postId}" class="product-wrapper">
                <img src="${order.mainImage || '/images/gift-shop-post-img3.png'}" alt="">
                <div class="purchase-info">
                    <span class="badge-list-container">${order.purchaseCountry}</span>
                    <p class="info-title">${order.postTitle}</p>
                    <span class="badge-list-container amount">수량</span>
                    <span class="info-amount">1개</span>
                    <p class="info-price">${order.purchaseProductPrice} 원</p>
                </div>
            </a>

            <div class="info-container">
                <h2 class="address-wrap">판매자 정보</h2>
                <div class="seller-info-category"><span class="seller-info">판매자명</span>${order.sellerName}</div>
                <div class="seller-info-category"><span class="seller-info">배송방법</span>${formatDelivery(order.purchaseDeliveryMethod)}</div>
                <div class="seller-info-category"><span class="seller-info">전화번호</span>${formatPhone(order.sellerPhone)}</div>
            </div>

            <hr class="divider">

            <div class="info-container">
                <h2 class="address-wrap">구매자 정보</h2>
                <div class="buyer-info-category"><span class="buyer-info">구매자명</span>${order.guestName}</div>
                <div class="buyer-info-category"><span class="buyer-info">주소</span>${order.guestAddress} ${order.guestAddressDetail}</div>
                <div class="buyer-info-category"><span class="buyer-info">전화번호</span>${formatPhone(order.guestPhone)}</div>
            </div>
        </div>
    `;
    };

    // 상태 업데이트
    const updateOrderStatus = (statusText) => {
        const statusEl = document.querySelector(".header .status");
        if (statusEl) statusEl.innerText = statusText;
    };

    // 토스트 메시지
    const showToast = (message, reload = false) => {
        alert(message);
        if (reload) {
            location.reload();
        }
    };

    return {
        renderOrderDetail : renderOrderDetail,
        updateOrderStatus : updateOrderStatus,
        showToast : showToast
    };

})();
