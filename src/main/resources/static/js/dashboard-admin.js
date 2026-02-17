document.addEventListener("DOMContentLoaded", function () {

    fetch("/api/dashboard/admin")
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao carregar dados do dashboard.");
            }
            return response.json();
        })
        .then(data => {

            // Atualiza os cards
            document.getElementById("totalTorneios").textContent = data.totalTorneios;
            document.getElementById("totalTimes").textContent = data.totalTimes;
            document.getElementById("totalJogadores").textContent = data.totalJogadores;

            // Atualiza tabela de jogadores
            const tabela = document.getElementById("tabelaJogadores");
            tabela.innerHTML = "";

            if (!data.ultimosJogadores || data.ultimosJogadores.length === 0) {
                tabela.innerHTML = `
                    <tr>
                        <td colspan="3" class="text-center text-muted">
                            Nenhum jogador encontrado.
                        </td>
                    </tr>
                `;
                return;
            }

            data.ultimosJogadores.forEach(jogador => {

                const nomeTime = jogador.time ? jogador.time.nome : "-";

                tabela.innerHTML += `
                    <tr>
                        <td>${jogador.nome}</td>
                        <td>${jogador.perfil}</td>
                        <td>${nomeTime}</td>
                    </tr>
                `;
            });

        })
        .catch(error => {
            console.error("Erro no dashboard admin:", error);
        });

});