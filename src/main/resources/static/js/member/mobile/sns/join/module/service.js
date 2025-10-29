const memberService = (() => {
    const checkEmail = async (email) => {
        try{
            const response = await fetch(`/api/member/email-check?email=${email}`, {
                method:"POST"
            })

            if (response.ok) {
                const result = await response.json();
                console.log("결과:", result ? "사용 불가(중복)" : "사용 가능");
                return result;
            } else {
                console.error("에러 상태 코드:", response.status);
            }
        } catch (error) {
            console.error(error);
        }

    }
    return {checkEmail: checkEmail}
})();