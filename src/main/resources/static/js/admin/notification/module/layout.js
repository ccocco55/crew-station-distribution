const noticeLayout = (() => {
    // 목록 표시
    const showList = (result = {}) => {
        const notices  = result.noticeListAdmin || [];
        const listEl   = document.getElementById('noticeList');
        const pageWrap = document.querySelector('.notice-pagination');
        const criteria = result.criteria || {};

        if (!listEl) return;

        if (Array.isArray(notices) && notices.length) {
            listEl.innerHTML = notices.map(n => {
                const id     = n.id ?? '';
                const date   = n.createdMd ?? '';
                const title  = n.title ?? '(제목 없음)';
                const writer = n.writerName ?? 'CrewStation';
                return `
          <div class="notice-item" data-id="${id}">
            <div class="notice-date">${date}</div>
            <div class="notice-title">${title}</div>
            <div class="notice-writer">${writer}</div>
          </div>
        `;
            }).join('');
        } else {
            listEl.innerHTML = `<div class="empty">조회된 공지가 없습니다.</div>`;
        }

        // 페이지네이션
        if (pageWrap) {
            let p = '';
            const start = criteria.startPage ?? 1;
            const end   = criteria.endPage ?? 1;
            const cur   = criteria.page ?? 1;

            for (let i = start; i <= end; i++) {
                p += `<li data-page="${i}" style="cursor:pointer;${i===cur?'font-weight:bold;':''}">${i}</li>`;
            }
            pageWrap.innerHTML = p;
        }
    };

    // 상세보기 표시
    const showDetail = (n = {}) => {
        const detailSec = document.getElementById('noticeDetail');
        if (!detailSec) return;

        // 다른 섹션 숨기고 상세만 보이게
        document.querySelectorAll('#page-container > *').forEach(el => el.style.display = 'none');
        detailSec.style.display = 'block';

        // 백엔드 키 대응 폴백
        const title   = n.title ?? n.noticeTitle ?? '(제목 없음)';
        const content = n.content ?? n.noticeContent ?? '';
        const date    = n.createdMd ?? n.createdDatetime ?? n.createdDate ?? '-';
        const writer  = n.writerName ?? 'CrewStation';

        // DOM 채우기
        const $title  = document.getElementById('noticeDetailTitle');
        const $date   = document.getElementById('noticeDetailDate');
        const $cont   = document.getElementById('noticeDetailContent');
        const $writer = detailSec.querySelector('.writer');

        if ($title)  $title.textContent = title;
        if ($date)   $date.textContent  = date;
        if ($writer) $writer.textContent = writer;
        if ($cont)   $cont.innerHTML    = String(content).replace(/\n/g, '<br>');

        // 이미지(옵션)
        const imgWrap = document.getElementById('noticeDetailImgWrap');
        const imgs = n.images || n.files || [];
        if (imgWrap) {
            if (Array.isArray(imgs) && imgs.length) {
                imgWrap.innerHTML = imgs
                    .map(f => `<img src="${f.filePath || f}" alt="" style="max-width:200px;border-radius:8px;">`)
                    .join('');
                imgWrap.style.display = 'flex';
            } else {
                imgWrap.innerHTML = '';
                imgWrap.style.display = 'none';
            }
        }
    };

    return { showList, showDetail };
})();
