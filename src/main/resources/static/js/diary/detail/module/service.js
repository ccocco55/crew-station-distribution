const diaryDetailService = (() => {
    const like = async (like,isLike)=>{
        const method = isLike ? 'DELETE' : 'POST'
        const url = `/api/likes/${like.postId}`
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
    const report = async (report) => {
        let status = null;
        let message = null;
        let result = null;
        const response = await fetch(`/api/report/${report.postId}`, {
            method: 'POST',
            body: report.reportContent,
            headers: {
                'Content-Type': 'application/json'
            }

        });
        if (response.ok) {
            console.log("게시글 존재")
        } else if (response.status === 404) {
            console.log("게시글 없음")
        } else {
            // const error = await response.text()
            console.log(response);
        }
        message = await response.text();
        return {message: message, status: response.status}
    }

    const changeSecret = async (change) =>{
        let status = null;
        let message = null;
        let result = null;
        const response = await fetch(`/api/diaries/secret/${change.diaryId}`, {
            method: 'PUT',
            body: JSON.stringify(change.check),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (response.ok) {
            console.log("게시글 존재")
        } else if (response.status === 404) {
            console.log("게시글 없음")
        } else {
            const error = await response.text()
            console.log(error);
        }
        message = await response.text();
        return {message: message, status: response.status}
    }
    return {like:like,report:report,changeSecret:changeSecret}
})();


