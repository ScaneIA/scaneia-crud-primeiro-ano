document.addEventListener("DOMContentLoaded", function () {
    const parte1 = document.getElementById("parte1");
    const parte2 = document.getElementById("parte2");
    const flecha = document.getElementById("flecha");


    if (flecha) {
        flecha.addEventListener("click", () => {
            parte1.style.opacity = "0";
            parte1.style.display = "none";
            parte2.style.display = "block";
            parte2.style.opacity = "0";
            setTimeout(() => (parte2.style.opacity = "1"), 100);
        });
    }
});
