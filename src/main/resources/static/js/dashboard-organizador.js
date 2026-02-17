fetch("/api/dashboard/organizador")
    .then(response => response.json())
    .then(data => {

        // Atualiza cards
        document.getElementById("totalTorneios").textContent = data.totalTorneios;
        document.getElementById("totalTimes").textContent = data.totalTimes;
        document.getElementById("totalPartidas").textContent = data.totalPartidas;

        // Atualiza tabela
        const tabela = document.getElementById("tabelaTorneios");
        tabela.innerHTML = "";

        if (data.ultimosTorneios.length === 0) {
            tabela.innerHTML = `
                <tr>
                    <td colspan="4" class="text-center text-muted">
                        Nenhum torneio encontrado.
                    </td>
                </tr>
            `;
            return;
        }

        data.ultimosTorneios.forEach(torneio => {
            tabela.innerHTML += `
                <tr>
                    <td>${torneio.nome}</td>
                    <td>${torneio.jogo}</td>
                    <td>${torneio.status}</td>
                    <td>${torneio.dataInicio}</td>
                </tr>
            `;
        });

    })
    .catch(error => console.error("Erro ao carregar dashboard:", error));
