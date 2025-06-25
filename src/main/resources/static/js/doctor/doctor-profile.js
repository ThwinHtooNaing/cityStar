// showToast('success', 'Success!', 'Availability set successfully.');
// showToast('error', 'Error', 'Time must be at least 1 hour.');
// showToast("warning", "Warning", "You missed a field.");
// showToast('info', 'Note', 'Just an informational message.');

function setAvailability() {
    const start = document.getElementById("start-time").value;
    const end = document.getElementById("end-time").value;

  if (!start || !end) {
    showToast("warning", "Warning", "You missed a field.");
    // showToast('success', 'Success!', 'Availability set successfully.');
    // showToast('error', 'Error', 'Time must be at least 1 hour.');
    // showToast("warning", "Warning", "You missed a field.");
    // showToast('info', 'Note', 'Just an informational message.');
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
      availableDate: new Date().toLocaleDateString("en-CA", {
        timeZone: "Asia/Bangkok",
      }),
    startTime: `${String(startHour).padStart(2, "0")}:${String(
      startMin
    ).padStart(2, "0")}`,
    endTime: `${String(endHour).padStart(2, "0")}:${String(endMin).padStart(
      2,
      "0"
    )}`,
    doctor: null, // will be populated on the backend
  };

  console.log("Availability Data:", availabilityData);

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

document.addEventListener("DOMContentLoaded", () => {
  const startTimeInput = document.getElementById("start-time");
  const endTimeInput = document.getElementById("end-time");
  let originalStartTime = "";
  let originalEndTime = "";
  let changedStartTime = "";
  let changedEndTime = "";
  const setAvailabilityBtn = document.querySelector(
    "button[onclick='setAvailability()']"
  );
  setAvailabilityBtn.disabled = true;
    const todayInput = document.getElementById("today-date");
    todayInput.value = new Date().toLocaleDateString("en-CA", {
      timeZone: "Asia/Bangkok",
    });
    fetch('/doctor/availability')
        .then(response => {
            if (!response.ok) {
                throw new Error('No availability set for today');
            }
            return response.json();
        })
        .then(data => {
            startTimeInput.value = data.startTime;
            endTimeInput.value = data.endTime;
            originalStartTime = data.startTime.slice(0, 5);
            originalEndTime = data.endTime.slice(0, 5);
            setAvailabilityBtn.disabled = true;
        })
        .catch(error => {
            console.warn(error.message);
            // document.getElementById('today-date').value = today;
            document.getElementById('start-time').value = '';
            document.getElementById('end-time').value = '';
        });

        function checkIfChanged() {

          if(changedStartTime == originalStartTime &&
            changedEndTime == originalEndTime){
              setAvailabilityBtn.disabled = true;
            }else{
              setAvailabilityBtn.disabled = false;
            }
            
        }

        // Listen for input changes
        [startTimeInput, endTimeInput].forEach((input) => {
          input.addEventListener("input", () => {
            changedStartTime = startTimeInput.value.trim();
            changedEndTime = endTimeInput.value.trim();
            checkIfChanged();});
        });
});

const cameraIcon = document.querySelector(".camera-icon");
const fileInput = document.getElementById("profile-image-input");
const profileImage = document.querySelector(".profile-detail-image");
const updateBtn = document.getElementById("update-profile-btn");
const inputs = document.querySelectorAll(
  ".user-profile-detail-section input, .user-profile-detail-section textarea"
);

let originalImageSrc = profileImage.src;

const originalValues = {};
inputs.forEach((input) => {
  originalValues[input.id] = input.getAttribute("data-original");
});

cameraIcon.addEventListener("click", () => {
  fileInput.click();
});

function checkForChanges() {
  let hasChanged = false;

  
  inputs.forEach((input) => {
    if (input.value !== originalValues[input.id]) {
      hasChanged = true;
    }
  });

  if (profileImage.src !== originalImageSrc) {
    hasChanged = true;
  }

  updateBtn.style.display = hasChanged ? "block" : "none";
}

inputs.forEach((input) => {
  input.addEventListener("input", checkForChanges);
});

fileInput.addEventListener("change", (event) => {
  const file = event.target.files[0];
  if (file && file.type.startsWith("image/")) {
    const reader = new FileReader();
    reader.onload = function (e) {
      profileImage.src = e.target.result;
      checkForChanges();
    };
    reader.readAsDataURL(file);
  }
});

updateBtn.addEventListener("click", () => {
  const formData = new FormData();
  const changedData = {};

  inputs.forEach((input) => {
    if (input.value !== originalValues[input.id]) {
      changedData[input.name] = input.value;
    }
  });

  if (Object.keys(changedData).length > 0) {
    formData.append(
      "doctor",
      new Blob([JSON.stringify(changedData)], { type: "application/json" })
    );
  }
  let imagefilechange;
  if (fileInput.files.length > 0 && profileImage.src !== originalImageSrc) {
    formData.append("file", fileInput.files[0]);
    imagefilechange=true;
  }
  
  fetch("/doctor/profile", {
    method: "PATCH",
    headers: {
      [csrfHeader]: csrfToken, 
    },
    body: formData,
  })
    .then((response) => {
      if (!response.ok) throw new Error("Failed to update profile");
      return response.text();
    })
    .then((message) => {
      showToast("success", "Success!", message);
      updateBtn.style.display = "none";
      inputs.forEach((input) => {
        originalValues[input.id] = input.value;
        input.setAttribute("data-original", input.value);
      });
      originalImageSrc = profileImage.src;
      fileInput.value = "";
      setTimeout(()=> {
        showToast("info", "Note", "updating info pls wait");
      },1000)
      setTimeout(() => {
        // Update name
        if (changedData.firstName || changedData.lastName) {
          const fullName = `${
            changedData.firstName || originalValues["first-name"]
          } ${changedData.lastName || originalValues["last-name"] || ""}`;
          document
            .querySelectorAll(
              ".doctor-name span, .profile-name-text span:nth-child(2)"
            )
            .forEach((el) => {
              el.textContent = fullName;
            });
        }

        // Update specialization
        if (changedData.speciality) {
          document
            .querySelectorAll(
              ".specialization-text, .specialization-container .specialization"
            )
            .forEach((el) => {
              el.textContent = changedData.speciality;
            });
        }

        // Update contact info
        if (changedData.contactInfo) {
          document.querySelector(".contact-info-text").textContent =
            changedData.contactInfo;
        }

        // Update bio
        if (changedData.bio) {
          document.querySelector(".bio-text").textContent = changedData.bio;
        }

        // Update profile image if changed
        console.log("Selected file:", fileInput.files[0]);
        console.log(imagefilechange);
        if (imagefilechange) {
          const profileImageElement = document.querySelector(".img");
          if (profileImageElement) {
            profileImageElement.src = originalImageSrc;
          }
        }
        showToast("success", "Success!", "Profile Status Updated");
        imagefilechange = false;
      }, 3000);
    })
    .catch((err) => {
      console.error("Update error:", err);
      showToast("error", "Error", err);
    });
});