// const navLinks = document.querySelectorAll(".nav-link");
// const underline = document.querySelector(".tab-underline");
// const container = document.querySelector(".nav-li-container");

// function moveUnderlineTo(link) {
//   const linkRect = link.getBoundingClientRect();
//   const containerRect = container.getBoundingClientRect();
//   const left = linkRect.left - containerRect.left;
//   const width = linkRect.width;

//   underline.style.transform = `translateX(${left}px)`;
//   underline.style.width = `${width}px`;
// }

// navLinks.forEach((link) => {
//   link.addEventListener("click", () =>   {
//     navLinks.forEach((l) => l.classList.remove("active"));
//     link.classList.add("active");
//     moveUnderlineTo(link);
//   });
// });

// // Position underline on page load
// const activeLink = document.querySelector(".nav-link.active");
// if (activeLink) {
//   moveUnderlineTo(activeLink);
// }
