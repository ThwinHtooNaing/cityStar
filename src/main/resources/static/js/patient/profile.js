const cameraIcon = document.querySelector(".camera-icon");
const fileInput = document.getElementById("profile-image-input");
const profileImage = document.querySelector(".main-profile-image");

cameraIcon.addEventListener("click", () => {
  fileInput.click();
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
