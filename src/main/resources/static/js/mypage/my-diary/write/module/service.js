const diaryWriteService = (() => {
    const search = async (callback,search) => {
        const response = await fetch(`/api/tags?search=${search}`);
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Fetch error");
        }


        if(response.ok) {
            console.log("게시글 존재")
            const member = await response.json();
            if (callback) {
                callback(member);
            }

        } else {
            const error = await response.text()
            console.log(error);
        }

    }
    return {search: search}
})();