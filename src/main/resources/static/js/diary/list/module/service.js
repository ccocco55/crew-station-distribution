const diaryListService = (() => {
    const getDiaries = async (callback, page = 1, keyword = "",orderType="최신순",category="",check=false) => {
        console.log(page)
        try {
            const response = await fetch(`/api/diaries?page=${page}&keyword=${keyword}&orderType=${orderType}&category=${category}`)
            const result = await response.json();
            console.log(result);
            if (response.ok) {
                console.log("다이어리 목록 잘 나옴")
                setTimeout(  () => {
                     callback(result,check);
                }, 500)
            } else {
                const errorText = await response.text();
                console.log(response.status);
                console.log(errorText || "Fetch Error");
            }
            return result;
        } catch (error) {
            console.log(error);
        }
    }
    const like = async (like,isLike)=>{
        const method = isLike ? 'DELETE' : 'POST';
        const url = `/api/likes/${like.postId}`;
        console.log(JSON.stringify(like))
        try {
            const response = await fetch(url,{
                method: method,
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            const result = await response.text();
            return {message:result,status : response.status}
        } catch (error) {
            console.log(error);
        }
    }
    return {getDiaries: getDiaries,like:like}
})();


