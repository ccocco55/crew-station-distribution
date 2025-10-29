HTMLCollection.prototype.forEach = Array.prototype.forEach;
profileService.getMemberProfile(ProfileLayout.showProfile);
profileService.getDiary(ProfileLayout.showDiary);
profileService.getDiary(ProfileLayout.showCrewDiary);

const btns = document.getElementsByClassName("nav-button");
btns.forEach((btn)=>{
    btn.addEventListener("click",(e)=>{
        const diaryWarp = document.getElementById("diaryWarp");
        const crewDiaryWarp = document.getElementById("crewDiaryWarp");
        if( btn ===btns[0]){
            btns[0].classList.add('active');
            btns[1].classList.remove('active');
            diaryWarp.classList.add('active');
            crewDiaryWarp.classList.remove('active');
        }else{
            btns[0].classList.remove('active');
            btns[1].classList.add('active')
            diaryWarp.classList.remove('active');
            crewDiaryWarp.classList.add('active');
        }
    })
})

const profileWarp = document.getElementById("profileWarp");
const toast = document.querySelector(".toast");
profileWarp.addEventListener("click",(e)=>{
    if(e.target.closest(".share")){
        toast.style.display = "block";
        toast.classList.remove("hide");
        toast.classList.add("show");
        setTimeout(() => {
            toast.classList.remove("show");
            toast.classList.add("hide");
            setTimeout(() => {
                toast.style.display = "none";
            }, 500);
        }, 3000);
        clip();
    }
})
// 공유하기 버튼 클릭 이벤트
// const shareButton = document.querySelector(".share");
//
// shareButton.addEventListener("click", (e) => {

// });
//
function clip() {
    var url = "";
    var textarea = document.createElement("textarea");
    document.body.appendChild(textarea);
    url = window.location.href; // 현재 URL
    textarea.value = url;
    textarea.select();
    document.execCommand("copy");
    document.body.removeChild(textarea);
}
