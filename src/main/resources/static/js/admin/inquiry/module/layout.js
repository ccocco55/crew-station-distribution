const inquireLayout = (() => {
    const safe = (v, d='-') => (v === null || v === undefined || v === '') ? d : String(v);
    const esc  = (s) => String(s).replace(/[&<>"']/g, m => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m]));
    const nl2br = (s) => String(s).replace(/\n/g, '<br>');

    const $section = () => document.querySelector('#section-inquiry');
    const $tbody   = () => $section()?.querySelector('table tbody');
    const $total   = () => $section()?.querySelector('.count-amount');
    const $pillAns = () => $section()?.querySelectorAll('.pill-row .span-amount')[0];
    const $pillUnA = () => $section()?.querySelectorAll('.pill-row .span-amount')[1];

    const clear = () => {
        const tb = $tbody(); if (tb) tb.innerHTML = '';
        const t = $total(); if (t) t.textContent = '0';
        const a = $pillAns(); if (a) a.textContent = '0';
        const u = $pillUnA(); if (u) u.textContent = '0';
    };

    const showEmpty = () => {
        const tb = $tbody(); if (!tb) return;
        tb.innerHTML = `<tr><td colspan="6" class="text-center text-muted py-4">문의 내역이 없습니다.</td></tr>`;
        const t = $total(); if (t) t.textContent = '0';
        const a = $pillAns(); if (a) a.textContent = '0';
        const u = $pillUnA(); if (u) u.textContent = '0';
    };

    const showList = (list = []) => {
        const tb = $tbody(); if (!tb) return;
        tb.innerHTML = '';

        if (!Array.isArray(list) || list.length === 0) {
            showEmpty(); return;
        }

        let answered = 0, unanswered = 0;
        const frag = document.createDocumentFragment();

        list.forEach(it => {
            (it.inquiryStatus === 'ANSWERED' ? answered++ : unanswered++);
            const tr = document.createElement('tr');
            tr.dataset.id = it.id;

            tr.innerHTML = `
        <td class="text-list">${safe(it.id)}</td>
        <td>${esc(it.inquiryContent || '')}</td>
        <td>${safe(it.createdDatetime)}</td>
        <td>${it.inquiryStatus === 'ANSWERED' ? '답변완료' : '미답변'}</td>
        <td>${safe(it.replyDatetime)}</td>
        <td class="td-action text-center">
          <button type="button" class="action-btn view" data-id="${it.id}">
            <i class="mdi mdi-chevron-right"></i>
          </button>
        </td>`;
            frag.appendChild(tr);
        });

        tb.appendChild(frag);
        const t = $total(); if (t) t.textContent = String(list.length);
        const a = $pillAns(); if (a) a.textContent = String(answered);
        const u = $pillUnA(); if (u) u.textContent = String(unanswered);
    };

    const showDetail = (dto = {}) => {
        const modal = document.getElementById('modal');
        if (!modal) return;

        modal.dataset.inquiryId = dto.id;

        const badge = modal.querySelector('.modal-title .badge-label');
        if (badge) {
            const answered = !!dto.replyId;
            badge.textContent = answered ? '답변완료' : '미답변';
            badge.classList.toggle('text-primary', answered);
            badge.classList.toggle('text-danger', !answered);
        }

        // 문의정보 2단 테이블
        const infoTables = modal.querySelectorAll('.detail-info .info-table');
        if (infoTables[0]) {
            infoTables[0].querySelector('tbody').innerHTML = `
        <tr><th>문의번호</th><td>${safe(dto.id)}</td></tr>
        <tr><th>문의시간</th><td>${safe(dto.createdDatetime)}</td></tr>
      `;
        }
        if (infoTables[1]) {
            infoTables[1].querySelector('tbody').innerHTML = `
        <tr><th>문의 유형</th><td>-</td></tr>
        <tr><th>회원ID</th><td>${safe(dto.memberId)} (${esc(dto.memberName || '')})</td></tr>
      `;
        }

        // 문의내용
        const contentTable = modal.querySelectorAll('.detail-info .info-table')[2] ||
            modal.querySelector('.detail-info:nth-of-type(2) .info-table');
        if (contentTable) {
            contentTable.querySelector('tbody').innerHTML =
                `<tr><th>문의내용</th><td>${nl2br(esc(dto.inquiryContent || ''))}</td></tr>`;
        }

        // 답변 입력 초기화
        const inp = modal.querySelector('.payment-info input');
        if (inp) inp.value = '';
    };

    const openModal = () => {
        const modal = document.getElementById('modal'); if (!modal) return;
        modal.style.display = 'block';
        requestAnimationFrame(() => {
            modal.classList.add('show');
            modal.style.background = 'rgba(0,0,0,0.5)';
            document.body.classList.add('modal-open');
        });
    };

    const closeModal = () => {
        const modal = document.getElementById('modal'); if (!modal) return;
        modal.classList.remove('show');
        document.body.classList.remove('modal-open');
        setTimeout(() => { modal.style.display = 'none'; }, 100);
        delete modal.dataset.inquiryId;
    };

    return { clear, showEmpty, showList, showDetail, openModal, closeModal };
})();
