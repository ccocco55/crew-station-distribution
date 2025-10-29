document.addEventListener("DOMContentLoaded", () => {
    const searchBtn = document.getElementById("searchBtn");
    const searchInput = document.getElementById("keywordInput");

    if (!searchBtn || !searchInput) {
        console.warn("검색창 요소를 찾을 수 없습니다.");
        return;
    }

    function goSearch() {
        const keyword = searchInput.value.trim();
        const baseUrl = "/mypage/sale-list";
        const params = new URLSearchParams();

        if (keyword) params.append("keyword", keyword);
        params.append("page", 1);
        params.append("size", 10);

        window.location.href = `${baseUrl}?${params.toString()}`;
    }

    // 버튼 클릭 시 검색
    searchBtn.addEventListener("click", goSearch);

    // 엔터 키로 검색
    searchInput.addEventListener("keydown", (e) => {
        if (e.key === "Enter") {
            goSearch();
        }
    });

    document.querySelectorAll('.purchase-cancel').forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            const paymentStatusId = this.dataset.paymentStatusId;
            if (confirm("정말 이 판매 요청을 거절하시겠습니까?")) {
                updateSaleStatus(paymentStatusId, 'REFUND');
            }
        });
    });

    document.querySelectorAll('.accept').forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            const paymentStatusId = this.dataset.paymentStatusId;
            if (confirm("이 판매 요청을 수락하시겠습니까?")) {
                updateSaleStatus(paymentStatusId, 'PENDING');
            }
        });
    });

    // 상태 업데이트 함수
    function updateSaleStatus(paymentStatusId, newStatus) {
        if (!paymentStatusId) return;

        fetch(`/api/mypage/status/${paymentStatusId}?paymentPhase=${newStatus}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                paymentStatusId: paymentStatusId,
                status: newStatus
            })
        })
            .then(res => {
                if (!res.ok) throw new Error('상태 변경 실패');
                return res.text();
            })
            .then(() => {
                alert('성공적으로 반영되었습니다.');
                location.reload();
            })
            .catch(err => {
                console.error(err);
                alert('오류가 발생했습니다.');
            });
    }
});
