window.diaryReportInit = async function () {
    if (window.diaryReportInited) return;
    window.diaryReportInited = true;

    let diaryPage = 1;
    let diaryCheckScroll = true;
    let diaryHasMore = true;

    const loadReportDiaryList = async (page = 1) => {
        const reports = await diaryReportService.getReports(page);
        diaryReportLayout.showReportDiaryList(reports);
        diaryHasMore = Array.isArray(reports) && reports.length > 0;
    };

    await loadReportDiaryList(diaryPage);

    const DiaryScrollContainer = document.querySelector("#bootpay-main");
    if (DiaryScrollContainer && !DiaryScrollContainer.diaryScrollBound) {
        DiaryScrollContainer.diaryScrollBound = true;

        DiaryScrollContainer.addEventListener("scroll", async () => {
            const scrollTop = DiaryScrollContainer.scrollTop;
            const clientHeight = DiaryScrollContainer.clientHeight;
            const scrollHeight = DiaryScrollContainer.scrollHeight;

            if (scrollTop + clientHeight >= scrollHeight - 100) {
                if (diaryCheckScroll && diaryHasMore) {
                    diaryCheckScroll = false;
                    await loadReportDiaryList(++diaryPage);
                    setTimeout(() => {
                        if (diaryHasMore) diaryCheckScroll = true;
                    }, 800);
                }
            }
        });
    }


    // =============== 신고 모달 (상세/처리) ===============
    (() => {
        const modal = document.querySelector(".report-modal");
        if (!modal) return;

        const closeModal = () => {
            modal.classList.remove("show");
            document.body.classList.remove("modal-open");
            setTimeout(() => (modal.style.display = "none"), 120);
        };

        const bindText = (key, val) => {
            const el = modal.querySelector(`[data-bind="${key}"]`);
            if (el) el.textContent = val ?? "";
        };

        let currentRow = null;

        const openModalFromRow = (row) => {
            currentRow = row;

            const title = row.querySelector(".post-title")?.textContent.trim() ?? "-";
            const author = row.querySelector(".post-meta b")?.textContent.trim() ?? "-";
            const postIdText =
                row.querySelector(".post-meta .meta:last-child")?.textContent.trim() ?? "postId: -";
            const postId = postIdText.replace(/^postId:\s*/i, "") || "-";
            const reason = row.querySelector(".reason-badge")?.textContent.trim() ?? "-";
            const reporterName = row.querySelector("td:nth-child(3) b")?.textContent.trim() ?? "-";
            const reporterEmail = row.querySelector("td:nth-child(3) .text-muted")?.textContent.trim() ?? "-";
            const reportedAt = row.querySelector("td:nth-child(4)")?.textContent.trim() ?? "-";
            const badgeInRow = row.querySelector(".approval-status");

            bindText("title", title);
            bindText("author", author);
            bindText("postId", postId);
            bindText("reason", reason);
            bindText("reporterName", reporterName);
            bindText("reporterEmail", reporterEmail);
            bindText("reportedAt", reportedAt);

            const badgeInModal = modal.querySelector('[data-bind="statusBadge"]');
            if (badgeInModal) {
                badgeInModal.className = "status-badge";
                if (badgeInRow?.classList.contains("status-resolved")) {
                    badgeInModal.classList.add("status-resolved");
                    badgeInModal.textContent = "처리완료";
                } else if (badgeInRow?.classList.contains("status-rejected")) {
                    badgeInModal.classList.add("status-rejected");
                    badgeInModal.textContent = "반려";
                } else {
                    badgeInModal.classList.add("status-pending");
                    badgeInModal.textContent = "대기중";
                }
            }

            modal.style.display = "block";
            requestAnimationFrame(() => {
                modal.classList.add("show");
                modal.style.background = "rgba(0,0,0,.5)";
                document.body.classList.add("modal-open");
            });
        };

        document.addEventListener("click", (e) => {
            const btn = e.target.closest(".action-btn");
            if (!btn) return;
            const row = btn.closest("tr");
            if (!row) return;
            openModalFromRow(row);
        });

        const closeBtns = modal.querySelectorAll(".btn-close, .close");
        closeBtns.forEach((b) => b.addEventListener("click", closeModal));
        modal.addEventListener("click", (e) => { if (e.target === modal) closeModal(); });

        const approveBtn = modal.querySelector(".btn-approve");
        approveBtn &&
        approveBtn.addEventListener("click", async () => {
            if (!currentRow) return;

            const reportId = currentRow.dataset.reportId;
            const postIdText = currentRow.querySelector(".post-meta .meta:last-child")?.textContent.trim() ?? "";
            const postId = postIdText.replace(/^postId:\s*/i, "");
            const hidePost = modal.querySelector(".cb-hide-post")?.checked || false;

            const ok = await diaryReportService.processReport(reportId, postId, hidePost);
            if (!ok) {
                alert("신고 처리에 실패했습니다.");
                return;
            }

            const badge = currentRow.querySelector(".approval-status");
            if (badge) {
                badge.textContent = "처리완료";
                badge.classList.remove("status-pending", "status-rejected");
                badge.classList.add("status-resolved");
            }

            const badgeInModal = modal.querySelector('[data-bind="statusBadge"]');
            if (badgeInModal) {
                badgeInModal.className = "status-badge status-resolved";
                badgeInModal.textContent = "처리완료";
            }

            closeModal();
        });
    })();
};