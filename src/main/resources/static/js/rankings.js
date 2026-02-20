document.addEventListener("DOMContentLoaded", () => {
    // Pegando os elementos do seu HTML
    const selectTorneio = document.getElementById("selectTorneioRanking");
    const selectTime = document.getElementById("selectTimeRanking");
    const tabelaBody = document.getElementById("tabelaRankingBody");

    // 1. CARREGAR TORNEIOS NO SELECT
    function carregarTorneios() {
        fetch('/api/torneios')
            .then(res => res.json())
            .then(torneios => {
                selectTorneio.innerHTML = '<option value="">Selecione o torneio</option>';
                torneios.forEach(t => {
                    selectTorneio.innerHTML += `<option value="${t.id}">${t.nome}</option>`;
                });
            })
            .catch(err => console.error("Erro ao carregar torneios:", err));
    }

    // 2. CARREGAR TIMES NO SELECT
    function carregarTimes() {
        fetch('/api/times')
            .then(res => res.json())
            .then(times => {
                selectTime.innerHTML = '<option value="">Selecione o time</option>';
                times.forEach(t => {
                    selectTime.innerHTML += `<option value="${t.id}">${t.nome}</option>`;
                });
            })
            .catch(err => console.error("Erro ao carregar times:", err));
    }

    // 3. FUNÇÃO PARA RENDERIZAR A TABELA
    function renderizarTabela(dados) {
        tabelaBody.innerHTML = "";
        
        if (dados.length === 0) {
            tabelaBody.innerHTML = '<tr><td colspan="5" style="text-align:center;">Nenhum dado encontrado</td></tr>';
            return;
        }

        // Ordenar por pontuação (Garantia extra no Front-end)
        dados.sort((a, b) => b.pontuacao - a.pontuacao);

        dados.forEach((r, index) => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${index + 1}º</td>
                <td>${r.time.nome}</td>
                <td>${r.vitorias}</td>
                <td>${r.derrotas}</td>
                <td><strong>${r.pontuacao}</strong></td>
            `;
            tabelaBody.appendChild(tr);
        });
    }

    // 4. EVENTOS DE FILTRO
    selectTorneio.addEventListener("change", () => {
        const torneioId = selectTorneio.value;
        if (torneioId) {
            fetch(`/api/rankings/torneio/${torneioId}`)
                .then(res => res.json())
                .then(data => renderizarTabela(data));
        }
    });

    selectTime.addEventListener("change", () => {
        const timeId = selectTime.value;
        if (timeId) {
            fetch(`/api/rankings/time/${timeId}`)
                .then(res => res.json())
                .then(data => renderizarTabela(data));
        }
    });

    // Inicialização
    carregarTorneios();
    carregarTimes();
});