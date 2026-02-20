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
                data.forEach(time => {
                    tabela.innerHTML += `
                        <tr>
                            <td>${time.nome}</td>
                            <td>${time.torneio ? time.torneio.nome : '-'}</td>
                            <td>${time.historicoCompeticoes || '-'}</td>
                            <td>
                                <button class="btn btn-sm btn-warning" onclick="editarTime(${time.id})">Editar</button>
                                <button class="btn btn-sm btn-danger" onclick="excluirTime(${time.id})">Excluir</button>
                            </td>
                        </tr>`;
                });
            });
    }

    form.addEventListener("submit", (e) => {
        e.preventDefault();
        const id = document.getElementById("timeId").value;

        const timeData = {
            id: id ? parseInt(id) : null,
            nome: document.getElementById("nome_time").value,
            historicoCompeticoes: document.getElementById("historico").value,
            torneio: { id: parseInt(selectTorneio.value) }
        };

        const metodo = id ? "PUT" : "POST";
        const url = id ? `/api/times/${id}` : "/api/times";

        fetch(url, {
            method: metodo,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(timeData)
        }).then(() => {
            form.reset();
            document.getElementById("timeId").value = "";
            listarTimes();
            alert("Operação realizada com sucesso!");
        });
    });

    window.editarTime = (id) => {
        fetch(`/api/times/${id}`)
            .then(res => res.json())
            .then(time => {
                document.getElementById("timeId").value = time.id;
                document.getElementById("nome_time").value = time.nome;
                document.getElementById("historico").value = time.historicoCompeticoes;
                document.getElementById("id_torneio").value = time.torneio ? time.torneio.id : "";
                window.scrollTo(0, document.body.scrollHeight);
            });
    }

    window.excluirTime = (id) => {
        if (confirm("Excluir time?")) {
            fetch(`/api/times/${id}`, { method: "DELETE" }).then(() => listarTimes());
        }
    };

    carregarTorneios();
    listarTimes();
});