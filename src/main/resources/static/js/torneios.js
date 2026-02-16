// Executa quando a página terminar de carregar
document.addEventListener("DOMContentLoaded", () => {

    carregarTorneios();

    const form = document.getElementById("formTorneio");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const torneio = {
            nome: document.getElementById("nome").value,
            descricao: document.getElementById("descricao").value,
            dataInicio: document.getElementById("dataInicio").value,
            dataFim: document.getElementById("dataFim").value || null
        };

        try {
            const response = await fetch("/api/torneios", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(torneio)
            });

            if (!response.ok) {
                throw new Error("Erro ao criar torneio");
            }

            form.reset();
            carregarTorneios();

        } catch (error) {
            console.error("Erro:", error);
            alert("Erro ao salvar torneio.");
        }
    });

});

// FUNÇÃO PARA LISTAR TORNEIOS
async function carregarTorneios() {

    try {
        const response = await fetch("/api/torneios");

        if (!response.ok) {
            throw new Error("Erro ao buscar torneios");
        }

        const torneios = await response.json();

        const tabela = document.getElementById("tabelaTorneios");
        tabela.innerHTML = "";

        torneios.forEach(torneio => {

            const linha = document.createElement("tr");

            linha.innerHTML = `
                <td>${torneio.nome}</td>
                <td>${torneio.descricao ?? ""}</td>
                <td>${formatarData(torneio.dataInicio)}</td>
                <td>${torneio.dataFim ? formatarData(torneio.dataFim) : ""}</td>
                <td>
                    <button class="btn btn-danger btn-sm"
                        onclick="excluirTorneio(${torneio.id})">
                        Excluir
                    </button>
                </td>
            `;

            tabela.appendChild(linha);
        });

    } catch (error) {
        console.error("Erro:", error);
        alert("Erro ao carregar torneios.");
    }
}

// FUNÇÃO PARA EXCLUIR
async function excluirTorneio(id) {

    if (!confirm("Deseja realmente excluir este torneio?")) {
        return;
    }

    try {
        const response = await fetch(/api/torneios/${id}, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error("Erro ao excluir");
        }

        carregarTorneios();

    } catch (error) {
        console.error("Erro:", error);
        alert("Erro ao excluir torneio.");
    }
}

// FORMATAR DATA (YYYY-MM-DD → DD/MM/YYYY)
function formatarData(data) {
    const partes = data.split("-");
    return ${partes[2]}/${partes[1]}/${partes[0]};
}