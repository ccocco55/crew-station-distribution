const purchaseListService = (() => {
    const getPurchases = async (callback, page = 1, keyword = "") => {
        console.log(page)
        try {
            const response = await fetch(`/api/gifts?page=${page}&keyword=${keyword}`)
            const result = await response.json();
            console.log(result);
            if (response.ok) {
                console.log("기프트 목록 잘 나옴")
                setTimeout(  () => {
                     callback(result);
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
    return {getPurchases: getPurchases}
})();