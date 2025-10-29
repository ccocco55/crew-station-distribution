const checkService = (() => {
    const checkEmail = async (email) => {
        try{
            const response = await fetch(`/api/member/email-check?email=${email}`, {
                method:"POST"
            })

            if (response.ok) {
                const result = await response.json();
                console.log("결과:", result ? "정보 존재" : "정보 없음");
                return result;
            } else {
                console.error("에러 상태 코드:", response.status);
            }
        } catch (error) {
            console.error(error);
        }

    }

    const sendEmail = async (email) => {
        const response = await fetch(`/api/member/send-email?email=${email}`, {
            method:"POST"
        })

        if (response.ok) {
            const code = await response.json();
            console.log("결과:", code ? code : "실패");
            return code;
        } else {
            console.error("에러 상태 코드:", response.status);
        }

    }
    return {checkEmail: checkEmail, sendEmail: sendEmail}
})();