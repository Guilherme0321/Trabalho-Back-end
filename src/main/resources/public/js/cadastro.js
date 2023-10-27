document.querySelector("#form").addEventListener('submit', (e) => {
    e.preventDefault();
    let name = document.querySelector("#nome_completo");
    let email = document.querySelector("#Email").value;
    let senha = document.querySelector("#Senha").value;
    let tipo;
    if (document.querySelector("#Professor").checked == true) {
        tipo = 'professor';
    } else if (document.querySelector("#Aluno").checked == true) {
        tipo = 'aluno';
    }

    if (/^[a-zA-Z]+$/.test(name.value)) {
        var xml = new XMLHttpRequest();

        xml.open("POST", "/usuario", true);
        xml.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xml.onload = function () {
            if (xml.status >= 200 && xml.status < 300) {
                var resposta = xml.responseText;
                if(resposta == null){					
                	localStorage.setItem('acesso', "");
				}else{
	                localStorage.setItem('acesso', resposta);
				}
				document.querySelector("#form").submit();
            } else {
                console.error("Erro na requisição: " + xml.status);
            }
        };

        xml.send(`nome=${encodeURIComponent(name.value)}&email=${encodeURIComponent(email)}&senha=${encodeURIComponent(senha)}&tipo=${encodeURIComponent(tipo)}`);
    } else {
        name.placeholder = 'Nome Inválido!';
        name.value = '';
    }
});