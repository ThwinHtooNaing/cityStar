document.querySelectorAll(".form-group").forEach((inputElement) => {
  const inputField = inputElement.querySelector(".form-control");
  const togglePassword = inputElement.querySelector(".toggle-password");

  if (togglePassword) {
    togglePassword.addEventListener("click", () => {
      const isPassword = inputField.getAttribute("type") === "password";
      inputField.setAttribute("type", isPassword ? "text" : "password");
      togglePassword.innerHTML = isPassword
        ? '<i class="fas fa-eye-slash eye-icon"></i>'
        : '<i class="fas fa-eye eye-icon"></i>';
    });
  }

  inputField.addEventListener("input", () => {
    inputField.setAttribute("value", inputField.value);
  });
});

const doctorForm = document.getElementById("doctor_form");
const patientForm = document.getElementById("patient_form");
document.querySelectorAll(".tab").forEach((tab) => {
  tab.addEventListener("click", function () {
    document
      .querySelectorAll(".tab")
      .forEach((t) => t.classList.remove("active"));
    this.classList.add("active");
    const role = this.getAttribute("data-role");
    if (role === "doctor") {
      doctorForm.style.display = "block";
      patientForm.style.display = "none";
    } else if (role === "patient") {
      patientForm.style.display = "block";
      doctorForm.style.display = "none";
    }
  });
});