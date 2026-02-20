document.addEventListener("DOMContentLoaded", () => {
    const btnTema = document.getElementById("btn-tema");
    const body = document.body;

    // 1. Verifica se já existe uma preferência salva
    const temaSalvo = localStorage.getItem("tema");

    if (temaSalvo === "dark") {
        body.classList.add("dark-mode");
        btnTema.innerText = "☀️ Modo Claro";
    }

    // 2. Evento de clique para trocar o tema
    btnTema.addEventListener("click", () => {
        body.classList.toggle("dark-mode");
        
        if (body.classList.contains("dark-mode")) {
            localStorage.setItem("tema", "dark");
            btnTema.innerText = "☀️ Modo Claro";
        } else {
            localStorage.setItem("tema", "light");
            btnTema.innerText = "🌙 Modo Escuro";
        }
    });
});