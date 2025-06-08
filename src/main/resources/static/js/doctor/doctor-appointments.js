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
