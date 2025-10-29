window.closeAllLayerUIs = function () {
    document.querySelectorAll('.bt-pop-menu.show, .bt-pop-menu-back.show')
        .forEach(el => el.classList.remove('show'));

    document.querySelectorAll('#pop-menu-bt2.show')
        .forEach(el => el.classList.remove('show'));

    document.querySelectorAll('.modal.show, .payment-modal.show').forEach(m => {
        m.classList.remove('show');
        m.style.display = 'none';
    });
    document.body.classList.remove('modal-open');
};


window.inquiryInit = async function () {
    if (window._inquireInited) return;
    window._inquireInited = true;

    window.closeAllLayerUIs();
    const section = document.getElementById('section-inquiry');
    if (!section) return;

    window.closeAllLayerUIs = function () {
        document.querySelectorAll('.bt-pop-menu.show, .bt-pop-menu-back.show')
            .forEach(el => el.classList.remove('show'));
        document.querySelectorAll('#pop-menu-bt2.show')
            .forEach(el => el.classList.remove('show'));
        document.querySelectorAll('.modal.show, .payment-modal.show').forEach(m => {
            m.classList.remove('show');
            m.style.display = 'none';
        });
        document.body.classList.remove('modal-open');
    };


    const modal     = document.getElementById('inquiry-modal');
    const btnClose  = modal?.querySelector('[data-role="inquiry-close"]') || modal?.querySelector('.close');
    const btnReply  = modal?.querySelector('[data-role="inquiry-reply-submit"]');
    const replyInput= modal?.querySelector('.inquiry-reply input, .inquiry-reply textarea');

    const searchInput = section.querySelector('.filter-search input');
    const searchBtn   = section.querySelector('.filter-search .btn-search');

    // 필터 팝업(답변상태)
    const filterBtn   = section.querySelector('#button-filter-status');
    const filterPopup = section.querySelector('#pop-menu-bt2');
    const chkAns      = section.querySelector('#checkboxactive1'); // 답변완료
    const chkUnAns    = section.querySelector('#checkboxactive2'); // 미답변
    const btnAll      = section.querySelector('#allchecked1');
    const btnNone     = section.querySelector('#allflasechecked1');
    const btnApply    = filterPopup?.querySelector('.btn.btn-outline-primary.btn-sm');

    const tbody = section.querySelector('table tbody');

    const state = { keyword: '', category: '' }; // category: '', 'ANSWERED', 'UNANSWERED'

    const calcCategory = () => {
        const a = chkAns?.classList.contains('is-checked');
        const u = chkUnAns?.classList.contains('is-checked');
        if (a && !u) state.category = 'ANSWERED';
        else if (!a && u) state.category = 'UNANSWERED';
        else state.category = '';
    };

    const loadList = async () => {
        try {
            const list = await inquireService.getList({
                keyword: state.keyword,
                category: state.category
            });
            inquireLayout.showList(list);
        } catch (e) {
            console.error(e);
            inquireLayout.showEmpty();
        }
    };

    // 초기 데이터
    await loadList();

    // ===== 검색 =====
    searchBtn?.addEventListener('click', async (e) => {
        e.preventDefault();
        state.keyword = (searchInput?.value || '').trim();
        await loadList();
    });
    searchInput?.addEventListener('keydown', async (e) => {
        if (e.key === 'Enter') {
            state.keyword = (searchInput.value || '').trim();
            await loadList();
        }
    });

    // ===== 필터 팝업 토글 =====
    filterBtn?.addEventListener('click', (e) => {
        e.preventDefault();
        e.stopPropagation();
        if (!filterPopup) return;
        filterPopup.classList.toggle('show');
    });

    filterPopup?.addEventListener('click', (e) => {
        e.stopPropagation();
    });

    document.addEventListener('click', () => {
        const visible = section && section.offsetParent !== null;
        if (!visible) return;
        filterPopup?.classList.remove('show');
    });

    btnAll?.addEventListener('click', (e) => {
        e.preventDefault();
        chkAns?.classList.add('is-checked'); chkUnAns?.classList.add('is-checked');
    });
    btnNone?.addEventListener('click', (e) => {
        e.preventDefault();
        chkAns?.classList.remove('is-checked'); chkUnAns?.classList.remove('is-checked');
    });
    chkAns?.addEventListener('click', () =>
        chkAns.classList.toggle('is-checked'));

    chkUnAns?.addEventListener('click', () =>
        chkUnAns.classList.toggle('is-checked'));

    btnApply?.addEventListener('click', async (e) => {
        console.log("적용")
        e.preventDefault();
        calcCategory();
        filterPopup?.classList.remove('show');
        await loadList();
    });

    // ===== 목록 상세(모달) =====
    tbody?.addEventListener('click', async (e) => {
        const btn = e.target.closest('.action-btn.view, .td-action .action-btn');
        if (!btn) return;
        const id = btn.dataset.id || btn.closest('tr')?.dataset.id;
        if (!id) return;
        try {
            const dto = await inquireService.getDetail(id);
            inquireLayout.showDetail(dto);
            inquireLayout.openModal();
        } catch (err) {
            console.error('문의 상세 조회 실패', err);
            alert('상세 조회에 실패했습니다.');
        }
    });

    // 모달 닫기
    btnClose?.addEventListener('click', (e) => {
        console.log("클락");
        e.preventDefault(); inquireLayout.closeModal();
    });
    modal?.addEventListener('click', (e) => {
        if (e.target === modal) inquireLayout.closeModal();
    });
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && modal?.classList.contains('show')) inquireLayout.closeModal();
    });

    // 답변 등록
    btnReply?.addEventListener('click', async (e) => {
        e.preventDefault();
        const id  = modal?.dataset.inquiryId;
        const txt = (replyInput?.value || '').trim();
        if (!id) { alert('유효하지 않은 문의입니다.'); return; }
        if (!txt) { alert('답변 내용을 입력해 주세요.'); return; }

        try {
            await inquireService.postReply(id, txt);
            inquireLayout.closeModal();
            await loadList();
        } catch (err) {
            console.error('답변 등록 실패', err);
            alert('답변 등록에 실패했습니다.');
        }
    });
};
