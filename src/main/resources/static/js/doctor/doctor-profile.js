// showToast('success', 'Success!', 'Availability set successfully.');
// showToast('error', 'Error', 'Time must be at least 1 hour.');
// showToast("warning", "Warning", "You missed a field.");
// showToast('info', 'Note', 'Just an informational message.');

const todayInput = document.getElementById("today-date");
todayInput.value = new Date().toISOString().split("T")[0];

function setAvailability() {
    const start = document.getElementById("start-time").value;
    const end = document.getElementById("end-time").value;

  if (!start || !end) {
    showToast("warning", "Warning", "You missed a field.");
    return;
  }

    const [startHour, startMin] = start.split(":").map(Number);
    const [endHour, endMin] = end.split(":").map(Number);
    const startTotal = startHour * 60 + startMin;
    const endTotal = endHour * 60 + endMin;

  if (endTotal <= startTotal) {
    showToast("error", "Error", "start time must be greater than end time.");
    return;
  }

    const duration = endTotal - startTotal;
  if (duration < 60) {
    showToast("warning", "Warning", "Time must be at least 1 hour.");
    return;
  }

  const availabilityData = {
    availableDate: new Date().toISOString().slice(0, 10),
    startTime: `${String(startHour).padStart(2, "0")}:${String(
      startMin
    ).padStart(2, "0")}`,
    endTime: `${String(endHour).padStart(2, "0")}:${String(endMin).padStart(
      2,
      "0"
    )}`,
    doctor: null, // will be populated on the backend
  };

  fetch("/doctor/availability", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      [csrfHeader]: csrfToken,
    },
    body: JSON.stringify(availabilityData),
  })
    .then(async (response) => {
      const responseText = await response.text(); // Read plain text body

      if (!response.ok) {
        throw new Error(responseText || "Failed to save availability.");
      }

      showToast(
        "success",
        "Success!",
        responseText || "Availability set successfully."
      );
    })
    .catch((error) => {
      console.error("Error:", error);
      showToast("error", "Error", error.message || "Something went wrong.");
    });
}

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
