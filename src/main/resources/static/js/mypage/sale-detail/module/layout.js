// ===================== My Sale Detail Layout =====================
const saleLayout = (() => {

    const mapStatusText = (status) => {
        switch (status?.toLowerCase()) {
            case "request": return "요청 대기";
            case "pending": return "결제 대기";
            case "success": return "결제 완료";
            case "received": return "수령 완료";
            case "reviewed": return "리뷰 완료";
            case "refund": return "주문 취소";
            default: return status || "";
        }
    };

    function formatDateLabel(dateString) {
        if (!dateString) return "";
        const dateObj = new Date(dateString);
        const month = String(dateObj.getMonth() + 1).padStart(2, "0");
        const day = String(dateObj.getDate()).padStart(2, "0");
        const week = ["일", "월", "화", "수", "목", "금", "토"];
        const dayLabel = week[dateObj.getDay()];
        return `${month}/${day} (${dayLabel})`;
    }

    function formatPhone(phone) {
        if (!phone) return "";
        const digits = phone.replace(/\D/g, "");
        if (digits.length === 10) return digits.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3");
        if (digits.length === 11) return digits.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
        return phone;
    }

    const renderSaleDetail = (container, order) => {
        container.innerHTML = `
        <div class="main-wrap">
            <div class="product">
                <section class="header-wrapper">
                    <h1 class="header-title">
                        <span class="date">${formatDateLabel(order.updatedDatetime || order.createdDatetime)}</span>
                        <span>${mapStatusText(order.paymentPhase)}</span>
                    </h1>
                </section>

                <div class="product-wrapper">
                    <img src="${order.mainImage}" alt="상품 이미지">
                </div>

                <hr class="bar">

                <h2 class="address-wrap">주문 정보</h2>
                <div class="address">
                    <span class="address-span">제품</span>${order.postTitle}
                </div>
                <div class="address">
                    <span class="address-span">가격</span>${order.purchaseProductPrice.toLocaleString()} 원
                </div>
                <div class="address">
                    <span class="address-span">개수</span>1개
                </div>

                <hr class="bar">

                <h2 class="address-wrap">구매자 정보</h2>
                <div class="address">
                    <span class="address-span">구매자명</span>${order.memberName}
                </div>
                <div class="address">
                    <span class="address-span">전화번호</span>${formatPhone(order.memberPhone)}
                </div>

                <h2 class="address-wrap">배송지 정보</h2>
                <div class="address">
                    <span class="address-span">주소</span>
                    ${order.addressZipCode ? "(" + order.addressZipCode + ") " : ""}${order.address || ""} ${order.addressDetail || ""}
                </div>
                <div class="address">
                    <span class="address-span">받는 사람</span>${order.memberName}
                </div>
                <div class="address">
                    <span class="address-span">전화번호</span>${formatPhone(order.memberPhone)}
                </div>
            </div>
        </div>
    `;
    };

    return {
        renderSaleDetail: renderSaleDetail
    };

})();
