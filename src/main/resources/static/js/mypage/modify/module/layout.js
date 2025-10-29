document.addEventListener("DOMContentLoaded", async () => {
    try {
        const data = await mypageService.getMemberInfo();

        const defaultProfile =
            "https://image.ohousecdn.com/i/bucketplace-v2-development/uploads/default_images/avatar.png?w=144&h=144&c=c";

        const profileImg = document.querySelector(".profile-img");
        if (!profileImg) {
            console.error(".profile-img 요소를 찾을 수 없습니다.");
            return;
        }

        // 프로필 이미지 설정
        profileImg.src = data.profileImageUrl && data.profileImageUrl.trim() !== ""
            ? data.profileImageUrl
            : defaultProfile;
        profileImg.onerror = () => (profileImg.src = defaultProfile);

        // 안전하게 DOM 요소 존재할 때만 값 채움
        const emailInput = document.querySelector(".input-email");
        if (emailInput) emailInput.value = data.memberEmail || "";

        const nameInput = document.querySelector(".read-name");
        if (nameInput) nameInput.value = data.memberName || "";

        const birthInput = document.querySelector(".birth-name");
        if (birthInput) birthInput.value = data.memberBirth || "";

        const phoneInput = document.querySelector(".phone .input-input");
        if (phoneInput) phoneInput.value = data.memberPhone || "";

        const zipInput = document.querySelector(".address-code");
        if (zipInput) zipInput.value = data.zipCode || "";

        const addressInput = document.querySelector(".address");
        if (addressInput) addressInput.value = data.address || "";

        const addressDetailInput = document.querySelector(".address-detail");
        if (addressDetailInput) addressDetailInput.value = data.addressDetail || "";

        const snsInput = document.querySelector(".password-check");
        if (snsInput) snsInput.value = data.memberSocialUrl || "";

        initModifyEvents(); // 이벤트 초기화
    } catch (err) {
        console.error("마이페이지 정보 조회 실패:", err);
        alert("내 정보를 불러올 수 없습니다.");
    }
});
