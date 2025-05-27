document.querySelectorAll(".nav-link").forEach((item) => {
  item.addEventListener("click", function () {
    const link = this.querySelector("a");
    if (link) {
      window.location.href = link.href;
    }
  });
});

const profileImage = document.querySelector(".profile-image .img");
if (profileImage) {
  profileImage.addEventListener("click", function () {
    window.location.href = "/admin/profile";
  });
}

const toggler = document.querySelector(".toggler-container");
if (toggler) {
  toggler.addEventListener("click", function () {
    const aside = document.querySelector(".aside");
    if (aside) {
      aside.classList.toggle("collapsed");
    }
  });
}

function confirmLogout(event) {
  event.preventDefault();
  document.getElementById("logout-modal").style.display = "block";
}

function closeModal() {
  document.getElementById("logout-modal").style.display = "none";
}

function proceedLogout() {
  document.getElementById("loading-spinner").style.display = "block";
  document.getElementById("confirm-btn").disabled = true;

  // Simulate loading animation delay
  setTimeout(() => {
    document.getElementById("logout-form").submit();
  }, 1000); // 1 second delay
}

