// 결제 버튼
const paymentAccountButton = document.querySelectorAll("button.payment-account-button");
paymentAccountButton.forEach((button) => {
    button.addEventListener("click", () => {
        const item = button.dataset.item;
        const price = button.dataset.price;
        pay({ item, price });
    });
});

// 결제 처리
const pay = async ({ item, price }) => {
    try {
        const response = await Bootpay.requestPayment({
            application_id: "68affb6d836e97280fee7f28",
            price: price,
            order_name: item,
            order_id: "TEST_ORDER_" + new Date().getTime(),
            pg: "토스페이",
            user: {
                id: "회원아이디",
                username: "회원이름",
                phone: "01000000000",
                email: "test@test.com",
            },
            items: [
                {
                    id: "item_id",
                    name: item,
                    qty: 1,
                    price: price,
                },
            ],
            extra: { open_type: "iframe" }
        });

        switch (response.event) {
            case "done":
                console.log(response);

                // 서버에 결제 정보 전송
                await fetch("/api/payment/complete", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        paymentTransactionId: response.receipt_id,
                        paymentAmount: price,
                        paymentMethod: "tossPay",
                        paymentStatus: "success"
                    })
                });

                const quantity = priceToQuantity[price] || 0;

                const tableRow = document.querySelector(`#payment-${response.receipt_id}`);
                if(tableRow) {
                    const vitaCell = tableRow.querySelector(".payment-item.vita span");
                    if(vitaCell) {
                        vitaCell.textContent = (quantity > 0 ? "+" : "-") + quantity;
                        vitaCell.classList.remove("add", "del");
                        vitaCell.classList.add(quantity > 0 ? "add" : "del");
                    }
                }
                alert("결제가 완료되었습니다!");
                window.location.reload();
                break;

            case "confirm":
                await Bootpay.confirm();
                break;

            case "issued":
                console.log("가상계좌 발급 완료", response);
                break;
        }
    } catch (e) {
        console.error("결제 오류:", e);
        if (e.event === "cancel") alert("결제가 취소되었습니다.");
    }
};