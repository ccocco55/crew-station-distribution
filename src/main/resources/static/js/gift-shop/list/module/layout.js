const purchaseListLayout = (() => {
    const showPurchases = async (result) => {
        let text = ``;

        const purchases = result.purchaseDTOs;
        const tbody = document.querySelector("div.product-list-wrapper");
        console.log(tbody)
        if(result.criteria.total){
            console.log("실행")
            document.querySelector("div.search-page-empty").style.display = "none";
        }
        purchases.forEach((purchase) => {
                text += `
            <article class="product-card-wrapper">
                <a href="/gifts/detail/${purchase.postId}" class="purchase-link">
                <div class="product-image-container">
                    <div class="product-limit-time-wrapper" data-endtime="${purchase.limitDateTime}">
                    </div>
                    <div class="product-image-wrapper">
                        <div class="product-image-hover">
                            <img src="${purchase.filePath}" alt="상품-썸네일-이미지" class="product-image">
                        </div>
                    </div>
                </div>
                <div class="product-content-wrapper">
                    <div class="product-content-header">
                      <div class="product-content-nickname">${purchase.memberName}</div>
                      <span class="manner-wrap">
                        <img class="product-detail-header-manner-img" src="/images/manner.png" width="14" height="14" alt="케미지수 아이콘">
                        <span class="product-detail-header-manner">${purchase.chemistryScore}케미지수</span>
                      </span>
                    </div>
                    <div class="product-content-name">${purchase.postTitle}</div>
                    <div class="product-content-stock">${purchase.purchaseProductCount}개 남음</div>
                    <div class="product-content-price">${purchase.purchaseProductPrice} 원</div>
                    <div class="writer-info">
                    <span class="badge-list-container">
                        ${purchase.purchaseCountry} </span>
                    <span class="badge-list-container delivery-method">
                        ${purchase.purchaseDeliveryMethod === "direct" ? "직접전달" : "택배거래"} </span>
                    </div>

                </div>
                </a>
            </article>
            `
            })

        tbody.innerHTML += text;

    }

    return {showPurchases: showPurchases};
})();