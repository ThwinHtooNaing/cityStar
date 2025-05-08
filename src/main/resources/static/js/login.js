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
