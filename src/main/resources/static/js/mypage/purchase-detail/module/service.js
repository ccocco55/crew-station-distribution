// ===================== Order Service =====================
const orderService = (() => {

    // 주문 상세 조회
    const getOrderDetail = async (paymentStatusId) => {
        try {
            const res = await fetch(`/api/mypage/purchase-detail/${paymentStatusId}`);
            if (!res.ok) throw new Error("주문 상세 조회 실패");
            return await res.json();
        } catch (err) {
            console.error("주문 상세 조회 실패:", err);
            return null;
        }
    };

    // 주문 취소
    const cancelOrder = async (paymentStatusId) => {
        try {
            const res = await fetch(`/api/mypage/purchase-detail/${paymentStatusId}/status?paymentPhase=REFUND`, {
                method: "PUT"
            });
            if (!res.ok) throw new Error("주문 취소 실패");
            return await res.text();
        } catch (err) {
            console.error("주문 취소 실패:", err);
            throw err;
        }
    };

    // 결제 완료 (Bootpay done 이후 서버 통보)
    const completePayment = async (paymentData) => {
        try {
            const res = await fetch(`/api/payment/complete`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(paymentData)
            });

            const text = await res.text();
            if (!res.ok) throw new Error("결제 완료 처리 실패: " + text);

            console.log("결제 완료 서버 응답:", text);
            return text;
        } catch (err) {
            console.error("결제 완료 전송 실패:", err);
            throw err;
        }
    };

    // 수령 완료
    const completeReceive = async (paymentStatusId) => {
        try {
            const res = await fetch(`/api/mypage/purchase-detail/${paymentStatusId}/status?paymentPhase=RECEIVED`, {
                method: "PUT"
            });
            if (!res.ok) throw new Error("수령 완료 실패");
            return await res.text();
        } catch (err) {
            console.error("수령 완료 처리 실패:", err);
            throw err;
        }
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
        completePayment : completePayment,
        completeReceive : completeReceive,
        submitReview : submitReview,
    };

})();

