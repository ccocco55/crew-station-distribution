const mainLayout = (() => {
    const showMain = (result) => {

        const todayEl = document.getElementById("todayJoin");
        const monthlyEl = document.getElementById("monthlyJoins");
        const crewEl = document.getElementById("totalCrew");
        const memberEl = document.getElementById("totalMember");

        if (todayEl) todayEl.textContent = result?.todayJoin ?? 0;

        const lastMonthly = Array.isArray(result?.monthlyJoins)
            ? result.monthlyJoins[result.monthlyJoins.length - 1]
            : null;

        if (monthlyEl) monthlyEl.textContent = lastMonthly?.count ?? 0;

        if (crewEl) crewEl.textContent = result?.totalCrewCount ?? 0;
        if (memberEl) memberEl.textContent = result?.totalMemberCount ?? 0;
    };

    return { showMain };
})();
