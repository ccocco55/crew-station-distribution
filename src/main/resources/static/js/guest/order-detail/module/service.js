// ===================== Order Service =====================
const orderService = (() => {

    // 주문 상세 조회
    const getOrderDetail = async () => {
        try {
            const res = await fetch(`/api/guest/order-detail`);
            if (!res.ok) throw new Error("주문 상세 조회 실패");
            return await res.json();
        } catch (err) {
            console.error("주문 상세 조회 실패:", err);
            return null;
        }
    };

    // 주문 취소
    const cancelOrder = async () => {
        const res = await fetch(`/api/guest/order/status?paymentPhase=REFUND`, { method: "PUT" });
        if (!res.ok) throw new Error("주문 취소 실패");
        return await res.text();
    };

    // 결제하기
    const payOrder = async () => {
        const res = await fetch(`/api/guest/order/status?paymentPhase=SUCCESS`, { method: "PUT" });
        if (!res.ok) throw new Error("결제 실패");
        return await res.text();
    };

    // 수령 완료
    const completeReceive = async () => {
        const res = await fetch(`/api/guest/order/status?paymentPhase=RECEIVED`, { method: "PUT" });
        if (!res.ok) throw new Error("수령 완료 실패");
        return await res.text();
    };

    // 별점 주기 -> 케미지수 업데이트 + 주문 상태 변경
    const submitReview = async (sellerId, paymentStatusId, score) => {
        const res = await fetch(`/api/member/rating`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                sellerId: sellerId,
                paymentStatusId: paymentStatusId,
                rating: score
            })
        });
        if (!res.ok) throw new Error("리뷰 등록 실패");
        return await res.json();
    };

    return {
        getOrderDetail : getOrderDetail,
        cancelOrder : cancelOrder,
        payOrder : payOrder,
        completeReceive : completeReceive,
        submitReview : submitReview,
    };

})();
