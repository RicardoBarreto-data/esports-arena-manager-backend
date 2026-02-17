fetch("/api/me")
    .then(response => {
        if (!response.ok) {
            // Se o status for 403 ou 404, o erro será capturado aqui
            throw new Error("Não autenticado ou endpoint inexistente");
        }
        return response.json();
    })
    .then(user => {
        // Mapeia as permissões vindas do Spring Security
        const roles = user.authorities.map(auth => auth.authority);
        const isAdmin = roles.includes("ROLE_ADMINISTRADOR");
        const isOrganizer = roles.includes("ROLE_ORGANIZADOR");

        console.log("Roles detectadas:", roles); // Debug para ver se o ROLE_ está vindo certo

        // --- LÓGICA PARA ESCONDER ELEMENTOS ---
        
        if (!isAdmin) {
            document.querySelectorAll(".admin-only").forEach(el => el.style.display = "none");
            const linkJogadores = document.querySelector('a[href="jogadores.html"]');
            if (linkJogadores) linkJogadores.style.display = "none";
        }

        if (!isOrganizer) {
            document.querySelectorAll(".organizer-only").forEach(el => el.style.display = "none");
            
            // CORREÇÃO AQUI: Usando crases e seletor CSS correto
            ["torneios.html", "times.html", "partidas.html", "rankings.html"].forEach(link => {
                const el = document.querySelector(`a[href="${link}"]`); 
                if (el) el.style.display = "none";
            });
        }
    })
    .catch((error) => {
        console.error("Erro na verificação de sessão:", error);
        // COMENTE A LINHA ABAIXO PARA PARAR O PISCA-PISCA E VER O ERRO NO CONSOLE
        // window.location.href = "/login.html"; 
    });