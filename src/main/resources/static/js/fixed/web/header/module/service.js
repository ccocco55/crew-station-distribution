const memberService = (() => {
    const login = async (member) => {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            body: JSON.stringify(member),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Fetch error");
        }

        return await response.json();
    }

    const refresh = async () => {
        const response = await fetch('/api/auth/refresh', {
            method: 'POST',
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Fetch error");
        }

        return await response.json();
    }

    const logout = async () => {
        const response = await fetch('/api/auth/logout', {
            method: 'POST',
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Fetch error");
        }
    }

    const info = async (callback) => {
        const response = await fetch('/api/auth/info');
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Fetch error");
        }

        const member = await response.json();

        if(callback) {
            callback(member);
        }
    }

    const profile = async (memberId) => {
        const response = await fetch(`/api/member/${memberId}`);
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Fetch error");
        }
        return await response.json();
    };

    const alarmService = (() => {
        async function updateCount() {
            try {
                const response = await fetch("/api/alarms/count");
                const data = await response.json();
                const el = document.querySelector(".alarm-count");

                if (!el) return;
                const count = data.count ?? 0;

                if (count > 0) {
                    el.textContent = count;
                    el.classList.remove("hidden");
                } else {
                    el.classList.add("hidden");
                }
            } catch (err) {
                console.error("알림 개수 불러오기 실패:", err);
            }
        }
        return { updateCount };

    })();




    return {login: login, refresh: refresh, logout: logout, info: info, profile: profile, alarmService: alarmService};
})();


const loginSection = document.querySelector('.login-section');
const userSection = document.querySelector('.user-section');
const profileImg = document.querySelector('.member-profile-wrap img');
const nameEl = document.querySelector('.member-name');
const alarmCount = document.querySelector('.alarm-count');

// 기본 상태: 비로그인
if (loginSection) loginSection.classList.remove('hidden');
if (userSection) userSection.classList.add('hidden');

// 로그인 정보 불러오기
memberService.info(async (member) => {
    if (!member) {
        // 비로그인 상태 유지
        if (loginSection) loginSection.classList.remove('hidden');
        if (userSection) userSection.classList.add('hidden');
        return;
    }

    // 로그인 상태
    if (loginSection) loginSection.classList.add('hidden');
    if (userSection) userSection.classList.remove('hidden');


    // 프로필가져오기
    memberService.info(async (member) => {
        if (!member) return;
        const id = member.id;
        let imgUrl;

        if (member.socialImgUrl && member.socialImgUrl.trim() !== "") {
            imgUrl = member.socialImgUrl;
        }
        else if (id) {
            const profile = await memberService.profile(id);
            imgUrl = profile?.filePath || "https://image.ohousecdn.com/i/bucketplace-v2-development/uploads/default_images/avatar.png?w=144&h=144&c=c";
        }
        else {
            imgUrl = "https://image.ohousecdn.com/i/bucketplace-v2-development/uploads/default_images/avatar.png?w=144&h=144&c=c";
        }

        if (profileImg) {
            profileImg.src = imgUrl;
        }

        if (nameEl) nameEl.textContent = member.memberName || member.memberEmail;
    });


    // 이름 or 이메일 표시
    if (nameEl) {
        nameEl.textContent = member.memberName || member.memberEmail;
    }

});