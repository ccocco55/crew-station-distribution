const replyLayout = (() => {
    const showList = (repliesCriteria) => {
        const replyWrap = document.getElementById("replyWrap");
        const replyPageWrap = document.getElementById("replyPageWrap");
        let text = ``;
        let checkUser = ``;
        let src = ``;
        repliesCriteria.replies.forEach((reply) => {
            if(reply.filePath){
                src = reply.filePath;
            }else if(reply.socialImgUrl){
                src=reply.socialImgUrl;
            }else{
                src= "https://image.ohousecdn.com/i/bucketplace-v2-development/uploads/default_images/avatar.png?w=144&h=144&c=c"
            }
            if(reply.writer){
                checkUser =`
                <div class="report-container">
                                                <div class="point">・</div>
                                                <button class="modify-reply-button reply-button">수정</button>
                                            </div>
                                            <div class="report-container">
                                                <div class="point">・</div>
                                                <button class="remove-reply-button reply-button">삭제</button>
                                            </div>`
            }else{
                checkUser=``;
            }
            text += `
                <div class="reply-item" data-id="${reply.id}">
                                    <div class="profile-image">
                                        <img src="${src}" class="reply-profile-img">
                                    </div>
                                    <div class="reply-content">
                                        <div class="reply-name">
                                            <div class="name-text">${reply.memberName}</div>
                                        </div>
                                        <div style="display: flex;">
                                            <div class="reply-content-text">${reply.replyContent}</div>
                                        </div>
                                        <div class="reply-like">
                                            <div class="time">${reply.relativeDate}</div>
                                            <!-- 본인일때 x -->
                                            
                                            <div class="report-container">
                                                <div class="point">・</div>
                                                <button class="reply-report-button reply-button">신고하기</button>
                                            </div>
                                            <!-- 본인 댓글만 -->
                                            ${checkUser}
                                        </div>
                                    </div>
                                </div>
            `;
        });
        checkUser = ``;
        replyWrap.innerHTML = text;

        text = ``;
        let criteria = repliesCriteria.criteria;
        if(!criteria.total) return;
        if(criteria.hasPreviousPage){
            text = `<button class="number-button prev-next-button active" data-page="${criteria.startPage - 1}" class="paging">
    <span class="prev-next prev"></span>
</button>`
        }

        for(let i = criteria.startPage; i <= criteria.endPage; i++){
            text += `
            <button data-page="${i}" class="number-button ${criteria.page === i ? 'active' : ''}">${i}</button>
        `;
        }

        if(criteria.hasNextPage){
            text += `<button  data-page="${criteria.endPage + 1}" class="number-button prev-next-button active">
<span class="prev-next next"></span>
</button>`
        }

        replyPageWrap.innerHTML = text;
    }

    return {showList: showList}
})();












