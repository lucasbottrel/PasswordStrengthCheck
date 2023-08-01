// Função para buscar os dados JSON da URL
function buscarDados() {
    fetch("http://localhost:8080/")
        .then((response) => response.json())
        .then((data) => exibirDados(data))
        .catch((error) =>
            console.error("Erro ao buscar dados:", error)
        );
}

function getColor(complexidade){
    switch (complexidade) {
        case "Very Weak": return "#ff004a";
        case "Weak": return "orange";
        case "Good": return "yellow";
        case "Strong": return "green";
        case "Very Strong": return "blue";
    
    }
}

// Função para exibir os dados na tabela
function exibirDados(dados) {
    const table = document.getElementById("dados-table");
    dados.forEach((dado) => {

        const row = table.insertRow(-1);

        // Criação e adição da div na primeira célula
        const div1 = document.createElement('div');
        div1.textContent = dado.nome;
        div1.classList.add('divName');
        row.insertCell(0).appendChild(div1);

        // Criação e adição da div na segunda célula
        const div2 = document.createElement('div');
        div2.textContent = dado.complexidade;
        div2.classList.add('divComplexidade');
        div2.style.backgroundColor = getColor(dado.complexidade);
        row.insertCell(1).appendChild(div2);

        // Criação e adição da div na terceira célula
        const div3 = document.createElement('div');
        div3.textContent = dado.score;
        div3.style.backgroundColor = "gray"
        div3.classList.add('divScore');
        row.insertCell(2).appendChild(div3);

    });
}

function salvarCadastro() {
    var nome = document.getElementById("nome").value;
    var senha = document.getElementById("senha").value;

    // Cria o objeto JSON com os campos nome e senha
    var data = {
        nome: nome,
        senha: senha,
    };

    // Realiza a chamada POST para o endpoint usando o formato JSON
    fetch("http://localhost:8080/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
        },
        body: JSON.stringify(data),
    })
        .then((response) => response.json())
        .then((data) => {
            // Processa a resposta do servidor, se necessário
            console.log("Resposta do servidor:", data);
        })
        .catch((error) => {
            // Lida com erros na chamada
            console.error("Erro na chamada ao servidor:", error);
        });
}

// Chamar a função para buscar e exibir os dados
buscarDados();