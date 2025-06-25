const csrfToken = document
  .querySelector('meta[name="_csrf"]')
  .getAttribute("content");
const csrfHeader = document
  .querySelector('meta[name="_csrf_header"]')
  .getAttribute("content");

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

function showToast(
  type = "success",
  title = "Success",
  message = "Message here",
  duration = 5000
) {
  const container = document.getElementById("toast-container");
  const icons = {
    success: `<svg xmlns="http://www.w3.org/2000/svg" class="icon success-icon" viewBox="0 0 24 24" fill="none" stroke="#4CAF50" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 6L9 17l-5-5"/></svg>`,
    warning: `<svg xmlns="http://www.w3.org/2000/svg" class="icon warning-icon" viewBox="0 0 24 24" fill="none" stroke="#FF9800" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M10.29 3.86L1.82 18a1 1 0 0 0 .86 1.5h18.64a1 1 0 0 0 .86-1.5L13.71 3.86a1 1 0 0 0-1.72 0z"/><line x1="12" y1="9" x2="12" y2="13"/><line x1="12" y1="17" x2="12.01" y2="17"/></svg>`,
    info: `<svg xmlns="http://www.w3.org/2000/svg" class="icon info-icon" viewBox="0 0 24 24" fill="none" stroke="#2196F3" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="12" y1="16" x2="12" y2="12"/><line x1="12" y1="8" x2="12.01" y2="8"/></svg>`,
    error: `<svg xmlns="http://www.w3.org/2000/svg" class="icon error-icon" viewBox="0 0 24 24" fill="none" stroke="#f44336" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="15" y1="9" x2="9" y2="15"/><line x1="9" y1="9" x2="15" y2="15"/></svg>`,
  };
  const toast = document.createElement("div");
  toast.className = `toast ${type}`;
  toast.innerHTML = `
          <div class="toast-icon">${icons[type]}</div>
          <div class="toast-content">
          <div class="toast-title">${title}</div>
          <div class="toast-message">${message}</div>
          </div>
          <button class="close-btn" onclick="closeToast(this)">×</button>
      `;

  container.appendChild(toast);

  setTimeout(() => {
    toast.style.animation = "slideOut 0.4s ease forwards";
    setTimeout(() => toast.remove(), 400);
  }, duration);
}

function closeToast(button) {
  const toast = button.closest(".toast");
  toast.style.animation = "slideOut 0.4s ease forwards";
  setTimeout(() => toast.remove(), 400);
}