document.addEventListener("DOMContentLoaded", () => {
  const container = document.querySelector(".new-appointment-content");

  function fetchTodayTopPending() {
    fetch("/doctor/today-top-pending")
      .then((res) => res.json())
      .then((appointments) => renderCards(appointments));
  }

  function renderCards(appointments) {
    container.innerHTML = "";
    appointments.forEach((a) => {
      const div = document.createElement("div");
      div.className = "new-appointment-card";

      const time = new Date(a.appointmentTime).toLocaleString();

      div.innerHTML = `
          <div class="new-appointment-card-header">
              <div class="new-appointment-card-img-container">
                  <img class="new-appointment-card-img" alt="image" src="${a.patient.profilePath}">
              </div>
              <div class="new-appointment-card-data">
                  <span class="new-appointment-card-patient-name">${a.patient.firstName} ${a.patient.lastName}</span>
                  <span class="new-appointment-card-patient-age">${a.patient.age} years</span>                                     
              </div>
          </div>
          <div class="new-appointment-card-body">
              <div class="new-appointment-card-info">
                  <span class="new-appointment-date">${time}</span>
                  <svg class="calendar-icon" xmlns="http://www.w3.org/2000/svg" height="15" width="15" viewBox="0 0 24 24" fill="currentColor">
                      <path d="M19 4h-1V2h-2v2H8V2H6v2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 16H5V9h14v11zm0-13H5V6h14v1z"/>
                  </svg>
              </div>
              <div class="new-appointment-card-info-details">
                  <span class="new-appointment-info">${a.appointmentInfo}</span>
              </div>
          </div>
          <div class="new-appointment-card-footer">
              <button class="new-appointment-button accept-btn" data-id="${a.appointmentId}">Accept</button>
              <button class="new-appointment-button decline-btn" data-id="${a.appointmentId}">Decline</button>
          </div>`;
      container.appendChild(div);
    });

    setupActions();
  }

  function setupActions() {
    document.querySelectorAll(".accept-btn").forEach((btn) => {
      btn.addEventListener("click", () =>
        updateStatus(btn.dataset.id, "Confirmed")
      );
    });
    document.querySelectorAll(".decline-btn").forEach((btn) => {
      btn.addEventListener("click", () =>
        updateStatus(btn.dataset.id, "Cancelled")
      );
    });
  }

  function updateStatus(id, status) {
    fetch(`/doctor/update-status/${id}?status=${status}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
        [csrfHeader]: csrfToken,
      },
    }).then((res) => {
      if (res.ok) fetchTodayTopPending(); // refresh
    });
  }

  fetchTodayTopPending(); 
  loadDashboardStats();
});

function loadDashboardStats() {
  fetch("/doctor/appointment-stats")
    .then((res) => res.json())
    .then((stats) => {
      document.querySelectorAll(".card-data-text").forEach((el) => {
        const label = el.textContent.trim();
        const countElement = el.previousElementSibling;

        if (label === "Today Patients") {
          countElement.textContent = stats.todayPatients;
        } else if (label === "Appointments This Month") {
          countElement.textContent = stats.monthlyAppointments;
        } else if (label === "New Appointments") {
          countElement.textContent = stats.newAppointments;
        }
      });
    });
}
