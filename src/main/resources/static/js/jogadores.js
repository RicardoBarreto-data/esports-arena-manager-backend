document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formJogador");
    const tabelaBody = document.getElementById("tabelaJogadores");
    const selectTime = document.getElementById("selectTime");

    // 1. CARREGAR TIMES PARA O SELECT (FK)
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

    // 2. LISTAR JOGADORES NA TABELA
    function listarJogadores() {
        fetch('/api/jogadores')
            .then(res => res.json())
            .then(jogadores => {
                tabelaBody.innerHTML = "";
                jogadores.forEach(j => {
                    const tr = document.createElement("tr");
                    tr.innerHTML = `
                        <td>${j.nome}</td>
                        <td>${j.perfil}</td>
                        <td>${j.dadosContato}</td>
                        <td>${j.time ? j.time.nome : 'Sem Time'}</td>
                        <td class="text-center">
                            <button class="btn btn-sm btn-warning" onclick="editarJogador(${j.id})">Editar</button>
                            <button class="btn btn-sm btn-danger" onclick="excluirJogador(${j.id})">Excluir</button>
                        </td>
                    `;
                    tabelaBody.appendChild(tr);
                });
            })
            .catch(err => console.error("Erro ao listar jogadores:", err));
    }

    // 3. SALVAR OU ATUALIZAR JOGADOR
    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const id = document.getElementById("jogadorId")?.value; // Campo oculto para edição
        const jogadorData = {
            id: id ? parseInt(id) : null,
            nome: document.getElementById("nome").value,
            perfil: document.getElementById("perfil").value,
            dadosContato: document.getElementById("dadosContato").value,
            time: { id: parseInt(selectTime.value) } // Objeto Time obrigatório para a Entity
        };

        const metodo = id ? 'PUT' : 'POST';
        const url = id ? `/api/jogadores/${id}` : '/api/jogadores';

        fetch(url, {
            method: metodo,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(jogadorData)
        })
        .then(res => {
            if (res.ok) {
                alert("Jogador salvo com sucesso!");
                form.reset();
                if(document.getElementById("jogadorId")) document.getElementById("jogadorId").remove();
                listarJogadores();
            } else {
                alert("Erro ao salvar jogador. Verifique os dados.");
            }
        });
    });

    // 4. EXCLUIR JOGADOR
    window.excluirJogador = (id) => {
        if (confirm("Deseja realmente excluir este jogador?")) {
            fetch(`/api/jogadores/${id}`, { method: 'DELETE' })
                .then(() => listarJogadores());
        }
    };

    // 5. PREPARAR EDIÇÃO (Povoar formulário)
    window.editarJogador = (id) => {
        fetch(`/api/jogadores/${id}`) // Você precisará do GetMapping("/{id}") no Controller
            .then(res => res.json())
            .then(j => {
                document.getElementById("nome").value = j.nome;
                document.getElementById("perfil").value = j.perfil;
                document.getElementById("dadosContato").value = j.dadosContato;
                document.getElementById("selectTime").value = j.time.id;

                // Cria um campo oculto para o ID se não existir
                if (!document.getElementById("jogadorId")) {
                    const inputId = document.createElement("input");
                    inputId.type = "hidden";
                    inputId.id = "jogadorId";
                    form.appendChild(inputId);
                }
                document.getElementById("jogadorId").value = j.id;
                window.scrollTo(0, document.body.scrollHeight); // Rola para o formulário
            });
    };

    // Inicialização
    carregarTimes();
    listarJogadores();
});