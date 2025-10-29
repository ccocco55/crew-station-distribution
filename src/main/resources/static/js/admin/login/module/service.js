const memberService = (() => {
    const login = async (member) => {
        const response = await fetch('/api/admin/auth/login', {
            method: 'POST',
            body: JSON.stringify(member),
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Fetch error");
        }

        return await response.json();
    };

    const refresh = async () => {
        const response = await fetch('/api/admin/auth/refresh', {
            method: 'GET',
            credentials: 'include'
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Fetch error");
        }

        return await response.json();
    };

    const logout = async () => {
        const response = await fetch('/api/admin/auth/logout', {
            method: 'POST',
            credentials: 'include' //  쿠키 전달
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Fetch error");
        }
    };

    const info = async (callback) => {
        const response = await fetch('/api/admin/auth/info', {
            credentials: 'include'
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Fetch error");
        }

        const member = await response.json();

        if (callback) {
            callback(member);
        }
    };

    return { login, refresh, logout, info };
})();
