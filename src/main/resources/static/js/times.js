document.addEventListener("DOMContentLoaded", () => {
    const tabela = document.getElementById("tabelaTimes");
    const form = document.getElementById("formTime");
    const selectTorneio = document.getElementById("id_torneio");

    function carregarTorneios() {
        fetch("/api/torneios")
            .then(res => res.json())
            .then(data => {
                selectTorneio.innerHTML = '<option value="">Selecione um torneio...</option>';
                data.forEach(t => {
                    selectTorneio.innerHTML += `<option value="${t.id}">${t.nome}</option>`;
                });
            });
    }

    function listarTimes() {
        fetch("/api/times")
            .then(res => res.json())
            .then(data => {
                tabela.innerHTML = "";
                if (data.length === 0) {
                    tabela.innerHTML = '<tr><td colspan="4" class="text-center text-muted">Nenhum time encontrado.</td></tr>';
                    return;
                }
                data.forEach(time => {
                    tabela.innerHTML += `
                        <tr>
                            <td>${time.nome}</td>
                            <td>${time.torneio ? time.torneio.nome : '-'}</td>
                            <td>${time.historicoCompeticoes || '-'}</td>
                            <td>
                                <button class="btn btn-sm btn-danger" onclick="excluirTime(${time.id})">Excluir</button>
                            </td>
                        </tr>`;
                });
            });
    }

    form.addEventListener("submit", (e) => {
        e.preventDefault();
        const novoTime = {
            nome: document.getElementById("nome_time").value,
            historicoCompeticoes: document.getElementById("historico").value,
            torneio: { id: parseInt(selectTorneio.value) }
        };

        fetch("/api/times", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(novoTime)
        }).then(() => {
            form.reset();
            listarTimes();
        });
    });

    window.excluirTime = (id) => {
        if (confirm("Excluir time?")) {
            fetch(`/api/times/${id}`, { method: "DELETE" }).then(() => listarTimes());
        }
    };

    carregarTorneios();
    listarTimes();
});