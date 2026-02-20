document.addEventListener("DOMContentLoaded", () => {
    const tabela = document.getElementById("tabelaTorneios");
    const form = document.getElementById("formTorneio");

    window.carregarTorneios = function() {
        fetch("/api/torneios")
            .then(res => res.json())
            .then(data => {
                tabela.innerHTML = "";
                if (data.length === 0) {
                    tabela.innerHTML = '<tr><td colspan="6" class="text-center">Nenhum torneio.</td></tr>';
                    return;
                }
                data.forEach(t => {
                    tabela.innerHTML += `
                        <tr>
                            <td>${t.nome}</td>
                            <td>${t.jogo}</td>
                            <td>${t.status}</td>
                            <td>${t.dataInicio}</td>
                            <td>${t.dataFim || "-"}</td>
                            <td>
                                <button class="btn btn-sm btn-warning" onclick="editarTorneio(${t.id})">Editar</button>
                                <button class="btn btn-sm btn-danger" onclick="excluirTorneio(${t.id})">Excluir</button>
                            </td>
                        </tr>`;
                });
            });
    }

    form.addEventListener("submit", function (e) {
        e.preventDefault();
        const id = document.getElementById("torneioId").value;
        
        const torneioData = {
            id: id ? parseInt(id) : null,
            nome: document.getElementById("nome").value,
            jogo: document.getElementById("jogo").value,
            descricao: document.getElementById("descricao").value,
            status: document.getElementById("status").value,
            dataInicio: document.getElementById("dataInicio").value,
            dataFim: document.getElementById("dataFim").value || null
        };

        const metodo = id ? "PUT" : "POST";
        const url = id ? `/api/torneios/${id}` : "/api/torneios";

        fetch(url, {
            method: metodo,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(torneioData)
        })
        .then(res => {
            form.reset();
            document.getElementById("torneioId").value = ""; // Limpa o ID
            carregarTorneios();
            alert(id ? "Torneio atualizado!" : "Torneio criado!");
        });
    });

    window.editarTorneio = function(id) {
        fetch(`/api/torneios/${id}`)
            .then(res => res.json())
            .then(t => {
                document.getElementById("torneioId").value = t.id;
                document.getElementById("nome").value = t.nome;
                document.getElementById("jogo").value = t.jogo;
                document.getElementById("descricao").value = t.descricao;
                document.getElementById("status").value = t.status;
                document.getElementById("dataInicio").value = t.dataInicio;
                document.getElementById("dataFim").value = t.dataFim;
                window.scrollTo(0, document.body.scrollHeight);
            });
    }

    window.excluirTorneio = function (id) {
        if (!confirm("Excluir torneio?")) return;
        fetch(`/api/torneios/${id}`, { method: "DELETE" }).then(() => carregarTorneios());
    };

    carregarTorneios();
});