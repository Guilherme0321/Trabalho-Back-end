fetch('/professores') 
    .then(response => response.json())
    .then(data => {
		console.log(data)
        preencherTabela(data);
    })
    .catch(error => {
        console.error('Erro ao buscar dados do backend:', error);
    });


function preencherTabela(data) {
    const tabela = document.querySelector("#tabela > thead")

    data.forEach(professor => {
        const row = document.createElement('tr');
        row.innerHTML = `<td>${professor.id}</td><td>${professor.nome}</td><td>${professor.email}</td>`;
        tabela.appendChild(row);
    });
}

let acesso = localStorage.getItem('acesso');
acesso = JSON.parse(acesso);

if ((acesso == null || acesso.id == null) && !window.location.href.endsWith("login.html")) {
	window.open("login.html", "_self");
}

