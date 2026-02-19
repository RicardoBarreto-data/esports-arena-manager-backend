document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formPartida");
    const selectTorneio = document.getElementById("selectTorneio");
    const selectTimeA = document.getElementById("timeA");
    const selectTimeB = document.getElementById("timeB");
    const tabela = document.getElementById("tabelaPartidas");

    // 1. CARREGAR TORNEIOS NO SELECT
    function carregarTorneios() {
        fetch("/api/torneios")
            .then(res => res.json())
            .then(data => {
                selectTorneio.innerHTML = '<option value="">Selecione o Torneio</option>';
                data.forEach(t => {
                    selectTorneio.innerHTML += `<option value="${t.id}">${t.nome}</option>`;
                });
            })
            .catch(err => console.error("Erro ao carregar torneios:", err));
    }

    // 2. FILTRAR TIMES POR TORNEIO (Mágica do relacionamento)
    selectTorneio.addEventListener("change", () => {
        const idTorneio = selectTorneio.value;
        if (!idTorneio) return;

        // Usa a rota que criamos com @RequestParam
        fetch(`/api/times?torneioId=${idTorneio}`)
            .then(res => res.json())
            .then(times => {
                let options = '<option value="">Selecione</option>';
                times.forEach(time => {
                    options += `<option value="${time.id}">${time.nome}</option>`;
                });
                selectTimeA.innerHTML = options;
                selectTimeB.innerHTML = options;
            });
    });

    // 3. SALVAR NOVA PARTIDA
    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const dados = {
            dataHora: document.getElementById("dataHora").value,
            status: "AGENDADA",
            torneio: { id: parseInt(selectTorneio.value) },
            timeA: { id: parseInt(selectTimeA.value) },
            timeB: { id: parseInt(selectTimeB.value) }
        };

        if (dados.timeA.id === dados.timeB.id) {
            alert("Um time não pode jogar contra ele mesmo!");
            return;
        }

        fetch("/api/partidas", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dados)
        })
        .then(res => {
            if (res.ok) {
                alert("Partida agendada!");
                form.reset();
                listarPartidas();
            }
        });
    });

    // 4. LISTAR PARTIDAS
    function listarPartidas() {
        fetch("/api/partidas")
            .then(res => res.json())
            .then(partidas => {
                tabela.innerHTML = "";
                partidas.forEach(p => {
                    const concluida = p.status === "CONCLUIDA";
                    
                    // Formata a data para o padrão BR
                    const dataFormatada = new Date(p.dataHora).toLocaleString('pt-BR');

                    tabela.innerHTML += `
                        <tr>
                            <td>${dataFormatada}</td>
                            <td>${p.timeA.nome} vs ${p.timeB.nome}</td>
                            <td><span class="badge ${concluida ? 'bg-success' : 'bg-primary'}">${p.status}</span></td>
                            <td>${concluida ? `<strong>${p.pontuacaoA} x ${p.pontuacaoB}</strong>` : '---'}</td>
                            <td>
                                ${!concluida ? 
                                    `<button class="btn btn-sm btn-success" onclick="finalizarPartida(${p.id})">Finalizar</button>` : 
                                    `<span class="text-muted">Encerrada</span>`}
                                <button class="btn btn-sm btn-danger" onclick="excluirPartida(${p.id})">Excluir</button>
                            </td>
                        </tr>
                    `;
                });
            });
    }

    // 5. FINALIZAR PARTIDA (Com prompt para pontos)
    window.finalizarPartida = (id) => {
        const pA = prompt("Pontos do Time A (Casa):", "0");
        const pB = prompt("Pontos do Time B (Visitante):", "0");

        if (pA !== null && pB !== null) {
            const resultado = {
                pontuacaoA: parseInt(pA),
                pontuacaoB: parseInt(pB)
            };

            fetch(`/api/partidas/${id}/finalizar`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(resultado)
            })
            .then(res => {
                if (res.ok) {
                    alert("Resultado salvo!");
                    listarPartidas();
                }
            });
        }
    };

    // 6. EXCLUIR
    window.excluirPartida = (id) => {
        if (confirm("Deseja cancelar esta partida?")) {
            fetch(`/api/partidas/${id}`, { method: "DELETE" })
                .then(() => listarPartidas());
        }
    };

    carregarTorneios();
    listarPartidas();
});