const ProfileLayout =(()=>{
    const showProfile = (memberDTO)=>{
        const profileWarp = document.getElementById("profileWarp");
        let text =`<div class="sticky-container">
                    <div class="sticky-child">
                        <div class="profile">
                            <div class="share">
                                <button class="share-button">
                                    <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor" preserveAspectRatio="xMidYMid meet">
                                        <path d="M9.64 14.646a4.5 4.5 0 1 1 0-5.292l4.54-2.476a4.5 4.5 0 1 1 .63.795l-4.675 2.55c.235.545.365 1.146.365 1.777s-.13 1.232-.365 1.777l4.675 2.55a4.5 4.5 0 1 1-.63.795l-4.54-2.476zM18 8a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7zM6 15.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7zM18 23a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7z"></path>
                                    </svg>
                                </button>
                            </div>
                            <div class="profile-main">
                                <div class="img-wrap">
                                `
            if(memberDTO.socialImgUrl!=null){ text +=  `<img src="${memberDTO.socialImgUrl}" alt="">`}else{text +=  `<img src="${memberDTO.filePath}" alt="">`}

        text +=  `
                                </div>
                                <div class="profile-info">
                                    <div class="name">${memberDTO.memberName}</div>
                                    <div class="profile-info-sub">
                                        <div class="info-sub">
                                            <dlv class="numbers">
                                                <a >
                                                    <span class="post">게시물 수</span>
                                                    <span class="post-number">${memberDTO.diaryCount}</span>
                                                </a>
                                                <span class="b-bar">|</span>
                                                <a href="">
                                                    <span class="post">케미지수</span>
                                                    <span class="post-number">${memberDTO.chemistryScore}</span>
                                                </a>
                                            </dlv>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="bar"></div>
                            <div class="chatting">
                                <a onclick="window.location.href='/diaries'" class="open-chatting">다이어리<br>보러가기</a>
                            </div>
                        </div>
                    </div>
                </div>`;
        profileWarp.innerHTML=text;

    }
    const showDiary=(diaryDTO)=>{
        const diaryContent = document.getElementById("diaryContent");
        let text ='';
        let count = 0;
        diaryDTO.forEach((diaryDTO)=>{
            if(diaryDTO.crewName===null){
                text+=`<div class="diary-wrap">
                            <div class="diary-div">
                                <a onclick="window.location.href='/diaries/detail/${diaryDTO.diaryFilePath}'">
                                    <div class="diary-img-wrap">
                                        <img class="diary-img" src="${diaryDTO.diaryFilePath}">
                                        <div class="more-icon">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="18" fill="#FFFFFF" viewBox="0 0 18 18">
                                                <path d="M2.25 4.35a2.5 2.5 0 0 1 2.5-2.5h6.8a2.5 2.5 0 0 1 2.5 2.5v6.8a2.5 2.5 0 0 1-2.5 2.5h-6.8a2.5 2.5 0 0 1-2.5-2.5z"></path><path fill-rule="evenodd" d="M15.75 4.05a.8.8 0 0 1 .8.8v7a4.3 4.3 0 0 1-4.3 4.3h-7a.8.8 0 0 1 0-1.6h7a2.7 2.7 0 0 0 2.7-2.7v-7a.8.8 0 0 1 .8-.8" clip-rule="evenodd">
                                            </path>
                                            </svg>
                                        </div>
                                        <div class="date-tag">
                                            <span class="tag-span">${diaryDTO.createdDatetime.split(" ")[0]}</span>
                                        </div>
                                        <div class="info">
                                        </div>
                                        <p class="diary-title">${diaryDTO.postTitle}</p>
                                        <p class="diary-content">${diaryDTO.postContent}</p>
                                    </div>
                                </a>
                                <label class="checkbox">
                                    <div class="checkbox-inner">
                                        <div class="checkbox-in">
                                            <span class="checkbox-span"></span>
                                            <input type="checkbox" class="checkbox-input">
                                        </div>
                                    </div>
                                </label>
                            </div>
                        </div>`
                count++;
            }
            const name = document.getElementsByClassName("tag-name");
            name[0].innerHTML= "다이어리("+count+")";
        })
        diaryContent.innerHTML=text;
    }
    const showCrewDiary=(diaryDTO)=>{
        const crewDiaryContent = document.getElementById("crewDiaryContent");
        let text ='';
        let count = 0;
        diaryDTO.forEach((diaryDTO)=>{
            if(diaryDTO.crewName!==null){
                text+=`<div class="diary-wrap">
                            <div class="diary-div">
                                <a href="">
                                    <div class="diary-img-wrap">
                                        <img class="diary-img" src="${diaryDTO.diaryFilePath}">
                                        <div class="more-icon">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="18" fill="#FFFFFF" viewBox="0 0 18 18">
                                                <path d="M2.25 4.35a2.5 2.5 0 0 1 2.5-2.5h6.8a2.5 2.5 0 0 1 2.5 2.5v6.8a2.5 2.5 0 0 1-2.5 2.5h-6.8a2.5 2.5 0 0 1-2.5-2.5z"></path><path fill-rule="evenodd" d="M15.75 4.05a.8.8 0 0 1 .8.8v7a4.3 4.3 0 0 1-4.3 4.3h-7a.8.8 0 0 1 0-1.6h7a2.7 2.7 0 0 0 2.7-2.7v-7a.8.8 0 0 1 .8-.8" clip-rule="evenodd">
                                            </path>
                                            </svg>
                                        </div>
                                        <div class="date-tag">
                                            <span class="tag-span">${diaryDTO.createdDatetime.split(" ")[0]}</span>
                                        </div>
                                        <div class="info">
                                        </div>
                                        <p class="diary-title">${diaryDTO.postTitle}</p>
                                        <p class="diary-content">${diaryDTO.postContent}</p>
                                    </div>
                                </a>
                                <label class="checkbox">
                                    <div class="checkbox-inner">
                                        <div class="checkbox-in">
                                            <span class="checkbox-span"></span>
                                            <input type="checkbox" class="checkbox-input">
                                        </div>
                                    </div>
                                </label>
                            </div>
                        </div>`
                count++;
            }
            const name = document.getElementsByClassName("tag-name");
            name[1].innerHTML= "크루 다이어리("+count+")";
        })
        crewDiaryContent.innerHTML=text;
    }

    return{showProfile:showProfile,showDiary:showDiary,showCrewDiary:showCrewDiary}
})();
