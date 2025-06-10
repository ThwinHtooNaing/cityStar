// const profileImage = document.querySelector(".profile-image .img");
// if (profileImage) {
//   profileImage.addEventListener("click", function () {
//     // window.location.href = "/patient/profile";
//   });
// }
document.querySelectorAll(".nav-link").forEach((item) => {
  item.addEventListener("click", function () {
    const link = this.querySelector(".link");
    if (link) {
      window.location.href = link.href;
    }
  });
});

function toggleProfileModal() {
  const modal = document.getElementById("profileModal");
  modal.style.display = modal.style.display === "block" ? "none" : "block";
}

// Optional: hide when clicking outside
document.addEventListener("click", function (event) {
  const modal = document.getElementById("profileModal");
  const profile = document.querySelector(".profile-container");
  if (!profile.contains(event.target)) {
    modal.style.display = "none";
  }
});

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

  setTimeout(() => {
    document.getElementById("logout-form").submit();
  }, 1000);
}

const sessionTimeout = 10 * 60 * 1000;
const warningBefore = 1 * 60 * 1000;
const warningTime = sessionTimeout - warningBefore;

let warningTimer, expireTimer;

function showModal(id) {
  document.getElementById(id).style.display = "block";
}

function logout() {
  window.location.href = "/auth/login?timeout";
}
function continueSession() {
  document.getElementById("session-warning-modal").style.display = "none";
  window.location.reload();
  startSessionCountdown();
}
function startSessionCountdown() {
  clearTimeout(warningTimer);
  clearTimeout(expireTimer);

  warningTimer = setTimeout(() => {
    showModal("session-warning-modal");
  }, warningTime);

  expireTimer = setTimeout(() => {
    document.getElementById("session-warning-modal").style.display = "none";
    showModal("session-expired-modal");
  }, sessionTimeout);
}

["click", "keydown", "scroll"].forEach((event) => {
  document.addEventListener(event, () => {
    startSessionCountdown();
  });
});

window.onload = startSessionCountdown;