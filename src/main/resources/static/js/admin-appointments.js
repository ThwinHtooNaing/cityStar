document.querySelectorAll('.header-item').forEach(item => {
    item.addEventListener('click', event => {
        const target = event.target;
        if (target.classList.contains('active')) {
            return;
        }
        document.querySelectorAll('.header-item').forEach(i => i.classList.remove('active'));
        target.classList.add('active');
    });
})