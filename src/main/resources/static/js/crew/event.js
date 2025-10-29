const moreBtn = document.querySelector(".css-1x7qv6q");
moreBtn.addEventListener("click", (e) => {
    console.log(11111);
});

const categoryBtn = document.querySelectorAll(".css-ygosan");

categoryBtn.forEach((btn) => {
    btn.addEventListener("click", (e) => {
        console.log(111111);

        categoryBtn.forEach((button) => {
            button.classList.remove("active");
        });

        btn.classList.add("active");
    });
});

const crewList = document.querySelectorAll(".crewList");
