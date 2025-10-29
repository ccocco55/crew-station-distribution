bannerService.showList(bannerLayout.list);

const registerBtn = document.querySelector(".add-btn");
const addInput = document.querySelector("#banner-file");

registerBtn.addEventListener("click", (e) => {
    e.preventDefault();
    addInput.click();
});

addInput.addEventListener("change", async (e) => {
    const file = e.target.files && e.target.files[0];
    if (!file) return;
    await bannerService.insert(file);
    e.target.value = "";
    await bannerService.showList(bannerLayout.list);
});


document.addEventListener("click", async (e) => {
    const btn = e.target.closest('.reg-btn[data-action="delete"]');
    if (!btn) return;

    const li = btn.closest(".registered-item");
    const bannerId = btn.dataset.bannerId || li.dataset.bannerId;
    if (!confirm("배너를 삭제하시겠습니까?")) return;

    try {
        await bannerService.deleteBanner(bannerId);
        li.remove();
    } catch (err) {
        console.log(err);
        alert("삭제 중 오류가 발생했습니다.");
    }
});




