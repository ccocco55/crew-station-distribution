(() => {
    const memberKeywordInput = document.getElementById("memberKeyword");
    const memberKeywordBtn = document.getElementById("memberKeywordBtn");
    const loading = document.getElementById("loading");

    const showMembers = async (page = 1, keyword = "") => {
        const tbody = document.getElementById("membersTbody");
        try {
            if (loading) loading.style.display = "block";
            return await memberService.getMembers(memberLayout.showMembers, page, keyword);
        } catch (e) {
            console.error(e);
            if (tbody)
                tbody.innerHTML = `<tr><td colspan="7" class="text-center text-danger">회원 목록을 불러오는 중 오류가 발생했습니다.</td></tr>`;
        } finally {
            if (loading) loading.style.display = "none";
        }
    };

    // 검색 엔터
    if (memberKeywordInput) {
        memberKeywordInput.addEventListener("keydown", async (e) => {
            if (e.key === "Enter") await showMembers(1, memberKeywordInput.value.trim());
        });
    }

    // 검색 버튼
    if (memberKeywordBtn) {
        memberKeywordBtn.addEventListener("click", async () => {
            await showMembers(1, memberKeywordInput?.value.trim() || "");
        });
    }

    // 페이징
    document.addEventListener("click", async (e) => {
        const a = e.target.closest(".pagination.bootpay-pagination a.paging");
        if (!a) return;
        e.preventDefault();
        const page = Number(a.dataset.page || 1);
        const keyword = (memberKeywordInput?.value || "").trim();
        await showMembers(page, keyword);
    });

    // 상세 보기 버튼
    document.addEventListener("click", async (e) => {
        const btn = e.target.closest(".member-detail-btn");
        if (!btn) return;
        const memberId = btn.dataset.memberid;
        if (!memberId) return;
        try {
            const data = await memberService.getDetailMember(memberLayout.showMemberDetail, memberId);
            memberLayout.openModal();
        } catch (err) {
            console.error(err);
            alert("회원 상세정보를 불러오는 데 실패했습니다.");
        }
    });

    // 모달 닫기
    document.addEventListener("click", (e) => {
        if (e.target.matches(".btn-close") || e.target.matches(".modal .close i")) {
            memberLayout.closeModal();
        }
    });

    //  초기 로딩
    document.addEventListener("DOMContentLoaded", () => {
        showMembers(1, "");
    });
})();
