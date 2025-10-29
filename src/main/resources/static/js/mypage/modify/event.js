
function initModifyEvents() {
    const fileInput = document.querySelector("#profile");
    const profileImg = document.querySelector(".profile-img");

    // 프로필 이미지 미리보기
    fileInput.addEventListener("change", (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (event) => {
                profileImg.src = event.target.result;
            };
            reader.readAsDataURL(file);
        }
    });

    // 완료 버튼 클릭 (TODO: 업데이트 서비스 추가)
    const submitBtn = document.querySelector(".submit");
    submitBtn.addEventListener("click", (e) => {
        e.preventDefault();
        alert("회원 정보 수정 기능 추가 예정.");
    });
}
