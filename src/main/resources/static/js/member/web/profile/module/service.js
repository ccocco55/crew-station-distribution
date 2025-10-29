const profileService = (()=>{
    const path = window.location.pathname;
    const segments = path.split("/");
    const id = segments[3];
    console.log(segments);
    const getMemberProfile = async (callback)=>{
        const response = await fetch(`/api/member/profile/${id}`);
        const memberDTO = await response.json();

        if(callback){
            callback(memberDTO);
        }
        return memberDTO;

    }
    const getDiary = async (callback)=>{
        const response = await fetch(`/api/diaries/profile/${id}`);
        const diaryDTO = await response.json();
        console.log(diaryDTO);

        if(callback){
            callback(diaryDTO);
        }
        return diaryDTO;

    }
    return{getMemberProfile:getMemberProfile,getDiary:getDiary}
})();