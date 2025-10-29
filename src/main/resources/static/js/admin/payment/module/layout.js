const paymentLayout = (() => {
    const n = (v) => Number(v ?? 0).toLocaleString('ko-KR');
    const safe = (v, def = '-') => (v === null || v === undefined || v === '') ? def : String(v);

    const $section = () => document.querySelector('#section-payment');
    const $tbody   = () => $section()?.querySelector('#payment-tbody');
    const $count   = () => $section()?.querySelector('.receipt-count .count-amount');
    const getTbody = () => document.querySelector('#section-payment #payment-tbody');

    const clear = () => {
        const tb = $tbody();
        if (tb) tb.innerHTML = '';
        const cnt = $count();
        if (cnt) cnt.textContent = '0';
    };

    const showEmpty = () => {
        const tb = $tbody();
        if (!tb) return;
        tb.innerHTML = `<tr><td colspan="7" class="text-center text-muted py-4">조회된 결제가 없습니다.</td></tr>`;
        const cnt = $count();
        if (cnt) cnt.textContent = '0';
    };

    // 배열 또는 {content, totalCount} 모두 지원
    const normalizeList = (raw) => Array.isArray(raw) ? raw : (raw?.content || []);
    const getTotalCount = (raw, list) => {
        if (typeof raw?.totalCount === 'number') return raw.totalCount;
        return list.length;
    };

    const toPhaseKo = (phase) => {
        const s = String(phase ?? '').toUpperCase();
        if (s.includes('PROGRESS')) return '결제진행중';
        if (s.includes('SUCCESS'))  return '결제완료';
        if (s.includes('CANCEL'))   return '결제취소';
        return phase ?? '-';
    };

    const showPayments = (raw = []) => {
        const tb = $tbody();
        if (!tb) return;

        const list = normalizeList(raw);
        // 🔁 덮어쓰기 모드
        tb.innerHTML = '';

        if (!list.length) {
            showEmpty();
            return;
        }

        const frag = document.createDocumentFragment();

        list.forEach((p) => {
            const tr = document.createElement('tr');
            tr.dataset.paymentId = p.id;

            const statusText = safe(p.statusText ?? toPhaseKo(p.paymentPhase));
            const timeText   = safe(p.paidAt ?? p.updatedDatetime);
            const methodText = safe(p.paymentMethod ?? p.deliveryType);

            tr.innerHTML = `
        <td class="td-name"><div class="good-name">${safe(p.productName)}</div></td>
        <td class="td-amount text-right pr-4 font-weight-bold">
          ${n(p.amount)} <span class="amount-unit">원</span>
        </td>
        <td class="td-method"><div class="pq">${methodText}</div></td>
        <td class="td-method">
          <div class="pq">토스페이</div>
        </td>
        <td class="td-status">
          <div class="label-form">
            <span class="badge-label text-nowrap text-dark approval-status">${statusText}</span>
          </div>
        </td>
        <td class="td-at text-center"><div class="date-at text-dark">${timeText}</div></td>
        <td class="td-buyer text-center text-dark">
          <div class="buyer-wrapper">
            <div class="user-name">${safe(p.buyerName)}</div>
          </div>
        </td>
        <td class="td-action text-center">
          <button type="button" class="action-btn view" 
          data-paymentid="${p.id}">
            <i class="mdi mdi-chevron-right"></i>
          </button>
        </td>
      `;
            frag.appendChild(tr);
        });

        tb.appendChild(frag);

        const cnt = $count();
        if (cnt) cnt.textContent = String(getTotalCount(raw, list));
    };

    const showPaymentDetail = (detail = {}) => {
        const modal = document.getElementById('payment-modal');
        if (!modal) return;

        const set = (k, v) => {
            const el = modal.querySelector(`[data-bind="${k}"]`);
            if (el) el.textContent = safe(v);
        };

        set('productName', detail.productName);
        set('amount', detail.amount != null ? `${n(detail.amount)}원` : '-');
        set('buyerName', detail.buyerName);
        set('buyerPhone', detail.buyerPhone);
        set('buyerEmail', detail.buyerEmail);
        set('paidAt', detail.paidAt ?? detail.createdDatetime ?? detail.updatedDatetime);

        set('sellerName', detail.sellerName);
        set('sellerPhone', detail.sellerPhone);
        set('sellerEmail', detail.sellerEmail);
        set('listedAt', detail.listedAtText ?? detail.listedAt);
        set('deliveryType', detail.deliveryTypeText ?? detail.deliveryType);
        set('address', detail.address);
    };

    return { clear, showEmpty, showPayments, showPaymentDetail };
})();
