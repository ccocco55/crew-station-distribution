// ===================== Member Order Event =====================
document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("order-detail");
    const paymentStatusId = document.body.dataset.paymentStatusId;

    if (!paymentStatusId) {
        console.error("paymentStatusId가 없습니다. <body th:data-payment-status-id>가 비어있습니다.");
        container.innerHTML = "<p>주문 정보를 불러올 수 없습니다.</p>";
        return;
    }

    // 주문 상세 조회
    orderService.getOrderDetail(paymentStatusId)
        .then(order => {
            console.log("회원 주문 상세:", order);
            if (!order) {
                container.innerHTML = "<p>주문 내역이 없습니다.</p>";
                return;
            }

            // UI 렌더링
            orderLayout.renderOrderDetail(container, order);

            // 버튼
            const cancelBtn = container.querySelector(".cancel-btn");
            const paymentBtn = container.querySelector(".payment-btn");
            const receiveBtn = container.querySelector(".receive-btn");
            const reviewBtn = container.querySelector(".review-btn");

            // === 주문 취소 ===
            if (cancelBtn && cancelBtn.classList.contains("active")) {
                cancelBtn.addEventListener("click", () => {
                    if (confirm("정말 주문을 취소하시겠습니까?")) {
                        orderService.cancelOrder(order.paymentStatusId)
                            .then(() => {
                                orderLayout.updateOrderStatus("주문 취소");
                                orderLayout.showToast("주문이 취소되었습니다.", true);
                            })
                            .catch(() => orderLayout.showToast("주문 취소 실패. 다시 시도해주세요."));
                    }
                });
            }

            // === 결제하기 ===
            if (paymentBtn && paymentBtn.classList.contains("active")) {
                paymentBtn.addEventListener("click", async () => {
                    try {
                        const itemName = order.postTitle || "기프트샵 결제";
                        const amount = order.purchaseProductPrice || 1000;

                        const response = await Bootpay.requestPayment({
                            application_id: "68de1c1f00d008657455bbbf",
                            price: amount,
                            order_name: "기프트샵 - " + itemName,
                            order_id: "ORDER_" + order.postId,
                            pg: "토스",
                            user: {
                                id: order.buyerMemberId,
                                username: order.memberName,
                                phone: order.memberPhone,
                                email: order.memberEmail || "example@email.com"
                            },
                            items: [
                                {
                                    id: "trade_" + order.postId,
                                    name: itemName,
                                    qty: 1,
                                    price: amount
                                }
                            ],
                            extra: { open_type: "iframe" }
                        });

                        switch (response.event) {
                            case "done":
                                console.log("Bootpay done:", response);

                                try {
                                    await orderService.completePayment({
                                        paymentStatusId: order.paymentStatusId,
                                        purchaseId: order.purchaseId,
                                        memberId: order.buyerMemberId,
                                        receiptId: response.data.receipt_id,
                                        paymentAmount: response.data.price,
                                        method: response.data.method,
                                        status: "success"
                                    });

                                    orderLayout.updateOrderStatus("결제 완료");
                                    orderLayout.showToast("결제가 완료되었습니다.", true);
                                } catch (err) {
                                    orderLayout.showToast("결제 처리 중 오류가 발생했습니다.");
                                }
                                break;

                            case "confirm":
                                await Bootpay.confirm();
                                break;

                            case "issued":
                                console.log("가상계좌 발급:", response);
                                break;
                        }
                    } catch (e) {
                        console.error("결제 오류:", e);
                        if (e.event === "cancel") {
                            orderLayout.showToast("결제가 취소되었습니다.");
                        } else {
                            orderLayout.showToast("결제 처리 중 오류가 발생했습니다.");
                        }
                    }
                });
            }

            // === 수령 완료 ===
            if (receiveBtn && receiveBtn.classList.contains("active")) {
                receiveBtn.addEventListener("click", () => {
                    if (confirm("상품을 수령 완료 처리하시겠습니까?")) {
                        orderService.completeReceive(order.paymentStatusId)
                            .then(() => {
                                orderLayout.updateOrderStatus("수령 완료");
                                orderLayout.showToast("수령 확인이 완료되었습니다.", true);
                            })
                            .catch(() => orderLayout.showToast("수령 처리 실패. 다시 시도해주세요."));
                    }
                });
            }

            // === 리뷰 작성 ===
            if (reviewBtn && reviewBtn.classList.contains("active")) {
                reviewBtn.addEventListener("click", () => {
                    const modal = document.querySelector(".modal-wrapper");
                    const stars = modal.querySelectorAll("label.star");
                    const submitBtn = modal.querySelector(".button-submit");
                    const closeBtn = modal.querySelector(".close-button");

                    let selectedScore = 0;
                    stars.forEach(s => s.classList.remove("full"));
                    modal.style.display = "flex";

                    // 별점 선택
                    stars.forEach((star) => {
                        star.onclick = () => {
                            selectedScore = parseInt(star.dataset.point, 10);
                            stars.forEach((s, i) => {
                                s.classList.toggle("full", selectedScore >= i + 1);
                            });
                        };
                    });

                    // 별점 제출
                    submitBtn.onclick = (e) => {
                        e.preventDefault();
                        if (selectedScore >= 1 && selectedScore <= 5) {
                            if (confirm("별점을 등록하시겠습니까?")) {
                                orderService.submitReview(order.sellerId, order.paymentStatusId, selectedScore)
                                    .then(() => {
                                        modal.style.display = "none";
                                        orderLayout.showToast("별점이 정상적으로 등록되었습니다.", true);
                                        if (reviewBtn) reviewBtn.remove();
                                    })
                                    .catch(() => orderLayout.showToast("리뷰 등록 실패. 다시 시도해주세요."));
                            }
                        } else {
                            orderLayout.showToast("별점을 선택해주세요.");
                        }
                    };

                    closeBtn.onclick = () => {
                        modal.style.display = "none";
                    };
                });
            }
        })
        .catch(err => {
            console.error("주문 상세 조회 중 오류:", err);
            container.innerHTML = "<p>주문 정보를 불러오지 못했습니다.</p>";
        });
});
