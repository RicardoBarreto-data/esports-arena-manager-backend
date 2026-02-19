document.addEventListener("DOMContentLoaded", () => {
    console.log("JS de Torneios carregado com sucesso!");

    const tabela = document.getElementById("tabelaTorneios");
    const form = document.getElementById("formTorneio");

    // 1. FUNÇÃO CARREGAR
    window.carregarTorneios = function() {
        console.log("Buscando torneios na API...");
        fetch("/api/torneios")
            .then(res => res.json())
            .then(data => {
                console.log("Dados recebidos:", data);
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
                                <button class="btn btn-sm btn-danger" onclick="excluirTorneio(${t.id})">Excluir</button>
                            </td>
                        </tr>`;
                });
            })
            .catch(err => console.error("Erro ao listar:", err));
    }

    // 2. EVENTO SUBMIT
    form.addEventListener("submit", function (e) {
        e.preventDefault();
        console.log("Botão Criar clicado!");

        const novoTorneio = {
            nome: document.getElementById("nome").value,
            jogo: document.getElementById("jogo").value,
            descricao: document.getElementById("descricao").value,
            status: document.getElementById("status").value,
            dataInicio: document.getElementById("dataInicio").value,
            dataFim: document.getElementById("dataFim").value || null
        };

        console.log("Enviando para o Java:", novoTorneio);

        fetch("/api/torneios", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(novoTorneio)
        })
        .then(res => {
            if (!res.ok) throw new Error("Erro no servidor: " + res.status);
            return res.json();
        })
        .then(data => {
            console.log("Sucesso!", data);
            form.reset();
            carregarTorneios();
            alert("Torneio criado!");
        })
        .catch(err => {
            console.error("Erro no fetch POST:", err);
            alert("Erro ao criar: " + err.message);
        });
    });

    // 3. FUNÇÃO EXCLUIR
    window.excluirTorneio = function (id) {
        if (!confirm("Excluir torneio " + id + "?")) return;
        
        fetch(`/api/torneios/${id}`, { method: "DELETE" })
            .then(() => carregarTorneios())
            .catch(err => console.error("Erro ao excluir:", err));
    };

    carregarTorneios();
});