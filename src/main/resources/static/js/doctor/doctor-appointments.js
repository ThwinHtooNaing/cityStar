const tabs = document.querySelectorAll(".appointment-tabs .tab");
const underline = document.querySelector(".tab-underline");

function moveUnderlineTo(tab) {
  const tabRect = tab.getBoundingClientRect();
  const containerRect = tab.parentElement.getBoundingClientRect();

  const left = tabRect.left - containerRect.left;
  const width = tabRect.width;

  underline.style.transform = `translateX(${left}px)`;
  underline.style.width = `${width}px`;
}

tabs.forEach((tab) => {
  tab.addEventListener("click", () => {
    tabs.forEach((t) => t.classList.remove("active"));
    tab.classList.add("active");
    moveUnderlineTo(tab);
  });
});

// Initialize underline on load
const activeTab = document.querySelector(".tab.active");
if (activeTab) moveUnderlineTo(activeTab);

document.addEventListener("DOMContentLoaded", () => {
  const tabs = document.querySelectorAll(".tab");
  const tableBody = document.querySelector(".appointment-table-body");
  const underline = document.querySelector(".tab-underline");

  function fetchAppointments(status = "") {
    const url = status ? `/doctor/all/appointments?status=${status}` : "/doctor/all/appointments";
    fetch(url)
      .then((response) => response.json())
      .then((data) => renderTable(data, status));
  }

  function updateCounts() {
    fetch("/doctor/appointment-counts")
      .then((response) => response.json())
      .then((counts) => {
        tabs.forEach((tab) => {
          const label = tab.textContent.trim().split(" ")[0];
          const badge = tab.querySelector(".badge");
          switch (label) {
            case "All":
              badge.textContent = counts.all ?? 0;
              break;
            case "Pending":
              badge.textContent = counts.pending ?? 0;
              break;
            case "Confirmed":
              badge.textContent = counts.confirmed ?? 0;
              break;
            case "Canceled":
              badge.textContent = counts.cancelled ?? 0;
              break;
          }
        });
      });
  }

  function renderTable(appointments, status) {
    tableBody.innerHTML = ""; // clear old rows
    appointments.forEach((a) => {
      const tr = document.createElement("div");
      tr.className = "appointment-row";
      const id = `#${a.appointmentId.toString().padStart(3, "0")}`;
      const time = new Date(a.appointmentTime).toLocaleString();
      const isEditable = status === "" || status === "Pending";
      tr.innerHTML = `
              <div>${id}</div>
              <div><img class="appointment-img" src="${
                a.patient.profilePath
              }" alt="image"></div>
              <div>${a.patient.firstName} ${a.patient.lastName}</div>
              <div>${a.patient.age}</div>
              <div>${time}</div>
              <div>${a.appointmentInfo}</div>
              <div><span class="status-badge ${a.status.toLowerCase()}">${
        a.status
      }</span></div>
              <div>
                  ${
                    isEditable
                      ? `
                      <button class="action-button accept" data-id="${a.appointmentId}">Accept</button>
                      <button class="action-button decline" data-id="${a.appointmentId}">Decline</button>
                  `
                      : `
                      <button class="action-button accept" disabled>Accept</button>
                      <button class="action-button decline" disabled>Decline</button>
                  `
                  }
              </div>
          `;
      tableBody.appendChild(tr);
      tr.querySelectorAll(".action-button").forEach((btn) => {
        btn.addEventListener("click", () => {
          const appointmentId = btn.dataset.id;
          const newStatus = btn.classList.contains("accept")
            ? "Confirmed"
            : "Cancelled";

          fetch(`/doctor/update-status/${appointmentId}?status=${newStatus}`, {
            method: "PATCH",           
            headers: {
              [csrfHeader]: csrfToken
            }
          })
            .then((res) => {
              if (res.ok) {
                fetchAppointments(status); // reload table
                updateCounts(); // refresh counts
              }
            })
            .catch((err) => console.error("Status update failed", err));
        });
      });
    });
  }

  tabs.forEach((tab) => {
    tab.addEventListener("click", () => {
      document.querySelector(".tab.active").classList.remove("active");
      tab.classList.add("active");

      const label = tab.textContent.trim().split(" ")[0];
      let status = "";
      if (label === "Pending") status = "Pending";
      else if (label === "Confirmed") status = "Confirmed";
      else if (label === "Canceled") status = "Cancelled";

      fetchAppointments(status);
      updateCounts();
    });
  });

  fetchAppointments(); // Initial load with all appointments
  updateCounts();
});
