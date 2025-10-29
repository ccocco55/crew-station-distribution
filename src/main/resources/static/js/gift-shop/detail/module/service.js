const purchaseDetailService = (() => {
    const report = async (report) => {
        let status = null;
        let message = null;
        let result = null;
        const response = await fetch(`/api/report/${report.postId}`, {
            method: 'POST',
            body: report.reportContent,
            headers: {
                'Content-Type': 'application/json'
            },
        });
        if (response.ok) {
            console.log("기프트 존재")
        } else if (response.status === 404) {
            console.log("기프트 없음")
        } else {
            // const error = await response.text()
            console.log(response);
        }
        message = await response.text();
        console.log(response);
        return {message: message, status: response.status}
    }

    const requestToSell = async ({purchaseId,...request}) => {
        let message = null;
        let result = null;
        let isGuest = false;
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        };
        if (request) {
            options.body = JSON.stringify(request);
        }
        const response = await fetch(`/api/payment/${purchaseId}`, options);
        message = await response.json();
        if (response.ok) {
            console.log(message)
            console.log("기프트 존재")
        } else if (response.status === 404) {
            console.log("기프트 없음")
        } else {
            console.log("에러 발생");
        }
        console.log(message)
        return {isGuest: message.guest,message: message.message, status: response.status, count:message.count}
    }
    return {report: report, requestToSell: requestToSell}
})();