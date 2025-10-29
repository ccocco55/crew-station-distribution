window.paymentInit = async function () {
    if (window.paymentInited) return;
    window.paymentInited = true;

    window.closeAllLayerUIs();
    const section = document.getElementById("section-payment");
    if (!section) return;


    const modal =
        document.querySelector(".payment-modal") ||
        section.querySelector(".payment-modal");

    const STATUS_MAP = {
        "pay-progress": "PENDING",
        "pay-success" : "SUCCESS",
        "pay-cancel"  : "CANCEL",
    };
    const LABEL_TO_KEY = new Map([
            ['결제진행', 'pay-progress'],
            ['결제완료', 'pay-success'],
            ['결제취소', 'pay-cancel'],
        ]);

        section.querySelectorAll('#filter-status .boot-check, .filter-status .boot-check').forEach(item => {
            const label = item.querySelector('.boot-check-content')?.textContent?.trim();
            const icon  = item.querySelector('.btn-status') || item.querySelector('i.mdi') || item.querySelector('i');
            if (!icon) return;

            icon.classList.add('btn-status');
            if (!icon.dataset.target) {
                icon.dataset.target = LABEL_TO_KEY.get(label) || 'pay-progress';
            }

            const initiallyOn =
                item.classList.contains('active') ||
                item.classList.contains('checked') ||
                item.querySelector('.boot-check-box')?.classList.contains('checked') ||
                item.querySelector('.boot-check-box')?.classList.contains('is-checked') ||
                icon.classList.contains('is-checked');

        });

    const state = {
        page: 1,
        categories: [],
        keyword: "",
    };

    // 상단 결제 승인/취소 금액
    const loadSummary = async () => {
        try {
            const data = await paymentService.getSummary({
                categories: state.categories,
                keyword: getKeyword(),
            });
            const approvedEl = section.querySelector(".amount-section .revenue-box .span-amount");
            const canceledEl = section.querySelector(".amount-section .cancel-box .span-amount");
            if (approvedEl)
                approvedEl.textContent = Number(data.approvedAmount ?? 0).toLocaleString("ko-KR");
            if (canceledEl)
                if (canceledEl) {
                    const canceledOrPending = Number(
                        (data.canceledAmount ?? data.pendingAmount ?? 0)
                    );
                    canceledEl.textContent = canceledOrPending.toLocaleString("ko-KR");
                }
        } catch (e) {
        }
    };


    const getKeyword = () =>
        section.querySelector(".filter-search input.form-control")?.value?.trim() || "";

    const getSelectedTargets = () =>
        Array.from(section.querySelectorAll(".btn-status.is-checked"))
            .map(i => i.dataset.target)
            .filter(Boolean);

    const getSelectedCategories = () =>
        getSelectedTargets().map(t => STATUS_MAP[t]).filter(Boolean);

    // ==== 목록 로드 ====
    let isLoading = false;
    let hasMore = true;

    const loadList = async (p = 1) => {
        if (isLoading) return;
        isLoading = true;
        try {
            state.page = p;
            state.keyword = getKeyword();

            const list = await paymentService.getPayments(p, {
                categories: state.categories,
                keyword: state.keyword,
            });


            paymentLayout.showPayments(list);

            const arr = Array.isArray(list) ? list : list?.content || [];
            hasMore = Array.isArray(arr) && arr.length > 0;
        } catch (e) {
            const tbody = section.querySelector("#payment-tbody");
            if (tbody) {
                tbody.innerHTML =
                    `<tr><td colspan="8" class="text-center text-danger py-4">결제 목록을 불러오지 못했습니다.</td></tr>`;

            }
        } finally {
            isLoading = false;
        }
    };

    paymentLayout.clear();
    if (!paymentService || typeof paymentService.getSummary !== 'function') {
    } else {
    }
    await Promise.all([loadList(1), loadSummary()]);

    // ==== 상태 드롭다운 팝업 ====
    const btnOpen  = section.querySelector("#btn-filter-status, #button-filter-status, .boot-pop-checkbox-filter-btn");
    const pop      = section.querySelector("#filter-status .bt-pop-menu, .filter-status .bt-pop-menu");
    const back= pop?.querySelector(".bt-pop-menu-back");
    const ctx= pop?.querySelector(".bt-pop-menu-context");
    const pcheck = section.querySelector("#penddingcheck"); // 결제 진행중
    const scheck = section.querySelector("#successcheck"); // 결제 완료
    const ccheck = section.querySelector("#cancelcheck"); // 결제 취소
    const btnAll   = section.querySelector("#btn-select-all");
    const btnNone  = section.querySelector("#btn-deselect-all");
    const btnApply = section.querySelector("#btn-apply-status, .btn.btn-outline-primary.btn-sm");

    if (!btnOpen || !pop || !ctx) {
    } else if (!pop._bound) {
        pop._bound = true;


        const setCheckedForIcon = (icon, on) => {
            if (!icon) return;

            const li  = icon.closest(".list-item");
            li?.classList.toggle("active", on);

            icon.classList.toggle("is-checked", on);
            const item = icon.closest(".boot-check");
            const box  = item?.querySelector(".boot-check-box") || icon.closest(".boot-check-box");
            item?.classList.toggle("checked", on);
            box?.classList.toggle("is-checked", on);
            box?.classList.toggle("checked", on);
        };


        const toggleCheckedForIcon = (icon) => {
            if (!icon) return;
            const li = icon.closest(".list-item");
            const on = !(li?.classList.contains("active"));
            setCheckedForIcon(icon, on);
        };

        const openPop = () => {
            back?.classList.add("show");
            ctx.classList.add("show");
            pop?.classList.add("active");
            const hostRect = btnOpen.getBoundingClientRect();
            ctx.style.position = "fixed";
            ctx.style.top  = `${hostRect.bottom + 8}px`;
            ctx.style.left = `${hostRect.left}px`;
            if (back) back.style.zIndex = "2999";
            ctx.style.zIndex = "3001";
        };
        const closePop = () => {
            ctx.classList.remove("show");
            back?.classList.remove("show");
            pop?.classList.remove("active");
        };

        const calcCategory = () => {
            const a = pcheck?.classList.contains('is-checked');
            const u = scheck?.classList.contains('is-checked');
            if (a && !u) state.category = 'ANSWERED';
            else if (!a && u) state.category = 'UNANSWERED';
            else state.category = '';
        };

        //
        btnOpen.addEventListener("click", (e) => {
            console.log("열림")
            e.preventDefault();
            e.stopPropagation();
            ctx.classList.contains("show") ? closePop() : openPop();
        });

        back?.addEventListener("click", (e) => {
            console.log("닫힘")
            e.stopPropagation();
            closePop();
        });



        ctx.addEventListener("click", (e) => {
            e.stopPropagation();

            const item = e.target.closest(".list-item, .boot-check, li");
            if (!item) return;
            e.preventDefault();

            const icon =
                item.querySelector(".btn-status") ||
                item.querySelector(".boot-check-box i") ||
                item.querySelector("i");

            toggleCheckedForIcon(icon);
        });


        // 전체선택 / 선택취소
        btnAll?.addEventListener("click", (e) => {
            console.log("전체")
            e.preventDefault(); e.stopPropagation();
            section.querySelectorAll(".btn-status").forEach(i => setCheckedForIcon(i, true));
        });
        btnNone?.addEventListener("click", (e) => {
            console.log("전체취소")
            e.preventDefault(); e.stopPropagation();
            section.querySelectorAll(".btn-status").forEach(i => setCheckedForIcon(i, false));
        });

        // 적용
        btnApply?.addEventListener("click", async (e) => {
            e.preventDefault();
            e.stopPropagation();

            const cats = Array.from(section.querySelectorAll(".list-item.active [data-target]"))
                .map(el => STATUS_MAP[el.dataset.target])
                .filter(Boolean);

            if (!cats.length) return alert("최소 1개 이상 선택하세요.");

            state.categories = cats;
            closePop();
            await Promise.all([loadList(1), loadSummary()]);
        });

    }


    // ==== 검색 버튼/엔터 ====
    (function bindSearch() {
        const input = section.querySelector(".filter-search input.form-control");
        const btn   = section.querySelector(".filter-search .btn-search");

        btn?.addEventListener("click", (e) => {
            e.preventDefault();
            Promise.all([loadList(1), loadSummary()]);
        });

        input?.addEventListener("keydown", (e) => {
            if (e.key === "Enter") Promise.all([loadList(1), loadSummary()]);
        });
    })();

    // ==== 상세 모달 열기/닫기 , 상세조회 ====
    if (modal) {
        let currentRow = null;

        const open = () => {
            modal.style.display = "block";
            requestAnimationFrame(() => {
                modal.classList.add("show");
                modal.style.background = "rgba(0,0,0,0.4)";
                document.body.classList.add("modal-open");
            });
        };

        const close = () => {
            modal.classList.remove("show");
            document.body.classList.remove("modal-open");
            setTimeout(() => (modal.style.display = "none"), 100);
            delete modal.dataset.paymentId;
            if (currentRow) {
                currentRow.classList.remove("current");
                currentRow = null;
            }
        };

        // 닫기 버튼 및 바깥 영역
        modal.querySelectorAll(".btn-close, .close")
            .forEach((btn) => btn.addEventListener("click", close));

        modal.addEventListener("click", (e) => {
            if (e.target === modal) close();
        });

        document.addEventListener("keydown", (e) => {
            if (e.key === "Escape" && modal.classList.contains("show")) close();
        });

        // 상세보기 버튼
        const table = section.querySelector("table");
        if (table && !table.paymentRowBound) {
            table.paymentRowBound = true;

            table.addEventListener("click", async (e) => {
                const btn = e.target.closest(".action-btn, .btn-detail");
                if (!btn) return;

                const row = btn.closest("tr");
                const id  = btn.dataset.paymentid || row?.dataset.paymentId;
                if (!id) return;

                try {
                    const detail = await paymentService.getDetail(id);
                    paymentLayout.showPaymentDetail(detail);

                    modal.dataset.paymentId = id;
                    if (currentRow) currentRow.classList.remove("current");
                    if (row) {
                        row.classList.add("current");
                        currentRow = row;
                    }

                    open();
                } catch (err) {
                    alert("상세 조회에 실패했습니다.");
                }
            });
        }

        const approveBtn   = modal.querySelector(".btn-approve");
        const cancelBtn    = modal.querySelector(".btn-cancel");

        const getCurrentId = () =>
            modal.dataset.paymentId ||
            section.querySelector("tr[data-payment-id].current")?.dataset.paymentId;

        approveBtn?.addEventListener("click", async () => {
            const id = getCurrentId();
            if (!id) return;
            const ok = await paymentService.processPayment(id, "approve");
            if (ok) close();
            else alert("승인 처리 실패");
        });

        cancelBtn?.addEventListener("click", async () => {
            const id = getCurrentId();
            if (!id) return;
            const ok = await paymentService.processPayment(id, "cancel");
            if (ok) close();
            else alert("취소 처리 실패");
        });
    } else {
        const waitModal = setInterval(() => {
            const modalCheck = document.querySelector(".payment-modal");
            if (modalCheck) {
                clearInterval(waitModal);
                window.paymentInit();
            }
        }, 700);
    }
};

window.addEventListener("DOMContentLoaded", () => {
    if (document.getElementById("section-payment") && typeof window.paymentInit === "function") {
        window.paymentInit();
    }
});