const profileImage = document.querySelector(".profile-image .img");
if (profileImage) {
  profileImage.addEventListener("click", function () {
    window.location.href = "/patient/profile";
  });
}
document.querySelectorAll(".nav-link").forEach((item) => {
  item.addEventListener("click", function () {
    const link = this.querySelector(".link");
    if (link) {
      window.location.href = link.href;
    }
  });
});