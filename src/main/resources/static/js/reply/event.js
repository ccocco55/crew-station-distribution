let isReply = true;
const submitReportBtn = document.querySelector(".report-button-send");
const confirmReportYes = document.getElementById("confirmReportYes");
const confirmReportModal = document.getElementById("confirmReportModal");
const confirmReportNo = document.getElementById("confirmReportNo");
function selectFirstReportRadio() {
    const radioBtns = document.querySelectorAll(".radio-button");
    radioBtns.forEach((radioBtn, idx) => {
        const btn = radioBtn.querySelector(".radio");
        if (idx === 0) {
            radioBtn.classList.add("active");
            if (btn) btn.checked = true;
        } else {
            radioBtn.classList.remove("active");
            if (btn) btn.checked = false;
        }
    });
}
let page = 1;
let postId= +document.getElementById("postId").dataset.post;
let check = true;
console.log(page);
console.log(postId);

replyService.getList(postId,replyLayout.showList,page);


const pagination = document.getElementById("replyPageWrap");
pagination.addEventListener("click",async (e)=>{
    console.log(e.target.closest("button.number-button"));
    if(e.target.closest("button.number-button")){
        page = e.target.closest("button.number-button").dataset.page;
        await replyService.getList(postId,replyLayout.showList,page);
    }

})


const replyWriteBtn = document.querySelector("button.enter-button");
const replyInput = document.querySelector("input.input[type=text]");
replyWriteBtn.addEventListener("click",async (e)=>{
    if(!check) return;
    check = false;
    if(!replyWriteBtn.classList.contains("active")){
        return;
    }
    if(replyInput.value.trim()===""){
        console.log(123123)
        return;
    }
    const {status}=await replyService.write({"postId":postId,"replyContent":replyInput.value})
    if(status ===404){
        location.href = "/diaries"
    }else if(status===401 || status === 403){
        toastModal(message);
    }
    const repliesCriteria = await replyService.getList(postId,replyLayout.showList,1);
    document.querySelector("span.total").textContent = repliesCriteria.criteria.total;
    document.querySelector("span.go-reply-icon").nextElementSibling.textContent = repliesCriteria.criteria.total;
    replyInput.value = "";
    console.log(repliesCriteria.criteria.total);
    replyWriteBtn.classList.remove("active");
    setTimeout(()=>{
        check = true;
    },1500);
})
replyInput.addEventListener("keydown",async (e)=>{
    if(e.key === "Enter"){
        if(!check) return;
        check = false;
        if(!replyWriteBtn.classList.contains("active")){
            return;
        }
        if(replyInput.value.trim()===""){
            console.log(123123)
            return;
        }
        const {message,status}=await replyService.write({"postId":postId,"replyContent":replyInput.value})
        if(status ===404){
            location.href = "/diaries"
        } else if(status===401 || status === 403){
            toastModal(message);
        }

        const repliesCriteria = await replyService.getList(postId,replyLayout.showList,1);
        document.querySelector("span.total").textContent = repliesCriteria.criteria.total;
        document.querySelector("span.go-reply-icon").nextElementSibling.textContent = repliesCriteria.criteria.total;

        console.log(repliesCriteria.criteria.total)
        replyInput.value = "";
        replyWriteBtn.classList.remove("active");
        setTimeout(()=>{
            check = true;

        },1500);
    }
})


const removeModal = document.querySelector(".remove-modal-reply");
const removeNoButton = removeModal.querySelector(".remove-no-reply");
const removeOkButton = removeModal.querySelector(".remove-ok-reply");

const replyWrap=document.getElementById("replyWrap");
let replyTextDiv = null;
let modifyBtn = true;
replyWrap.addEventListener("click",async (e)=>{
    let target = e.target.closest("button.modify-reply-button");
    let cancleBtn = e.target.closest("button.modify-reply-button.modify-cancle");
    let delBtn = e.target.closest("button.remove-reply-button");
    let reportBtn = e.target.closest("button.reply-report-button");
    if(cancleBtn){
        cancleBtn.textContent =  "수정";
        const input = document.createElement("input");
        const newText = input.value;
        const replyContent = target.closest(".reply-content");
        const replyFlexBox = replyContent.querySelector(
            "div[style='display: flex;']"
        );
        console.log(replyTextDiv)
        replyFlexBox.innerHTML=""
        replyFlexBox.appendChild(replyTextDiv);
        target.classList.remove("modify-cancle");
        modifyBtn = true;
        // 새로운 텍스트 div 생성
    } else if(target){
        if(!modifyBtn) return;
        modifyBtn = false;
        target.textContent = "취소"
        target.classList.add("modify-cancle");
        const replyContent = target.closest(".reply-content");
        const replyFlexBox = replyContent.querySelector(
            "div[style='display: flex;']"
        );
        replyTextDiv = replyFlexBox.querySelector(
            ".reply-content-text"
        );
        console.log(replyTextDiv)
        const originalText = replyTextDiv.textContent;

        const input = document.createElement("input");

        input.type = "text";
        input.className = "reply-edit-input";
        input.value = originalText;

        // 저장 버튼 만들기
        const saveButton = document.createElement("button");
        saveButton.textContent = "저장";
        saveButton.className = "save-reply-button";

        // 기존 텍스트 제거하고 input, 버튼 만들기
        replyFlexBox.innerHTML = "";
        replyFlexBox.appendChild(input);
        replyFlexBox.appendChild(saveButton);

        // 다시 텍스트로
        saveButton.addEventListener("click", async (e) => {
            const newText = input.value;
            const id = saveButton.closest(".reply-item").dataset.id;
            // 새로운 텍스트 div 생성
            const newTextDiv = document.createElement("div");
            newTextDiv.className = "reply-content-text";
            newTextDiv.textContent = newText;

            // input과 버튼 제거하고 텍스트 복원
            replyFlexBox.innerHTML = "";
            replyFlexBox.appendChild(newTextDiv);
            const {status}= await replyService.modify({"postId" :postId,"replyId":id,"replyContent": newText})
            if(status ===404){
                location.href = "/diaries"
            }

            const repliesCriteria = await replyService.getList(postId,replyLayout.showList,1);
            document.querySelector("span.total").textContent = repliesCriteria.criteria.total;
            document.querySelector("span.go-reply-icon").nextElementSibling.textContent = repliesCriteria.criteria.total;
            modifyBtn = true;
        });
    }else if(delBtn){
        removeModal.style.display = "block";
        removeOkButton.dataset.id = delBtn.closest(".reply-item").dataset.id;
    }else if(reportBtn){
        isReply = true;
        reportModal.classList.add("active");
        confirmReportYes.dataset.id =reportBtn.closest(".reply-item").dataset.id;
    }

})

removeNoButton.addEventListener("click", () => {
    removeModal.style.display = "none";
    targetReply = null;
});


// 확인 버튼 클릭 시 reply-item 삭제 후 모달 닫기
removeOkButton.addEventListener("click", async () => {

    const {status}=  await replyService.remove({"postId" :postId,"replyId":removeOkButton.dataset.id});
    if(status ===404){
        location.href = "/diaries"
    }
    const repliesCriteria = await replyService.getList(postId,replyLayout.showList,1);
    document.querySelector("span.total").textContent = repliesCriteria.criteria.total;
    document.querySelector("span.go-reply-icon").nextElementSibling.textContent = repliesCriteria.criteria.total;
    removeModal.style.display = "none";
});
