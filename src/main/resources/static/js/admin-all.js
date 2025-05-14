document.querySelectorAll(".nav-link").forEach((item) => {
  item.addEventListener("click", function () {
    const link = this.querySelector("a");
    if (link) {
      window.location.href = link.href;
    }
  });
});
document.querySelector(".profile-image .img").addEventListener("click",function(){
    const link = "/admin/profile";
    window.location.href = link;
})
document
  .querySelector(".toggler-container")
  .addEventListener("click", function () {
    const aside = document.querySelector(".aside");
    aside.classList.toggle("collapsed");
  });