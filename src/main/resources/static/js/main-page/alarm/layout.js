const alarms = document.querySelectorAll(".alarm-content-link");

alarms.forEach(alarm => {
    alarm.addEventListener("click", async (e) => {
        e.preventDefault();

        const alarmId = alarm.dataset.id;
        const alarmType = alarm.dataset.type;

        try {
            const response = await fetch(`/api/alarms/read?alarmType=${alarmType}&alarmId=${alarmId}`, {
                method: "PUT"
            });

            if (response.ok) {
                const wrap = alarm.closest(".alarm-wrap");
                wrap.classList.remove("unread");
                wrap.classList.add("read");
            }
        } catch (err) {
            console.error("읽음 처리 실패:", err);
        }
    });
});