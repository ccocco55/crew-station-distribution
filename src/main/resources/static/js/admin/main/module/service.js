const mainService = (() => {
    const getMain = async (callback) => {
        try {
            const response = await fetch(`/api/admin`);
            if (!response.ok) {
                const err = await response.text().catch(() => "");
                console.error("GET /api/admin/ 실패:", response.status, err);
                return null;
            }
            const result = await response.json();

            if (callback) callback(result);
            return result;
        } catch (error) {
            console.error(error);
            return null;
        }
    };
    return { getMain };
})();
