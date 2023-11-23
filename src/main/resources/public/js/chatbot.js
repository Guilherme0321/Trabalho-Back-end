let acesso = localStorage.getItem('acesso');
acesso = JSON.parse(acesso);

if ((acesso == null || acesso.id == null) && !window.location.href.endsWith("login.html")) {
	window.open("login.html", "_self");
}else{
	if(acesso.tipo == 'aluno'){
    document.querySelector("#cadastrar_aula").remove();
}
}

const chatWindow = document.getElementById('chat-window');
    const userInput = document.getElementById('user-input');
    const input_user = document.getElementById('user-input');

    input_user.addEventListener("keydown", (e) => {
      if(e.key === 'Enter'){
			chamarRequisicao();
      }
    })
    
    function chamarRequisicao(){
		sendMessage();
        enviarRequisicao();
	}
    
   function enviarRequisicao(){
	   let x = document.querySelector("#user-input").value
	   fetch("/listarAula?serach=" + x + '&id_aluno=' + acesso.id)
	   	.then(function(res){
		return res.text();
		})
		.then(function(response){
			userInput.value = '';
			sendFromChat(response);
		})
		.catch(function(error){
			console.error(error);
		});
   }
   
   function sendFromChat(text){
	   let jsonAula = JSON.parse(text);
	   Object.values(jsonAula).forEach(aula => {
			chatWindow.innerHTML += `<div class='chat'><p>${aula.titulo},${aula.descricao},${aula.preco},${aula.data_horario},${aula.tipo},${aula.link}</p></div>`;
	   })
   }

    function sendMessage() {
      const message = userInput.value;
      chatWindow.innerHTML += `<div class='you'><p> ${message}</p></div>`;
    }
    
   document.addEventListener("DOMContentLoaded", function() {
	let acesso = localStorage.getItem('acesso');
	acesso = JSON.parse(acesso);
	
	if ((acesso == null || acesso.id == null) && !window.location.href.endsWith("login.html")) {
		window.open("login.html", "_self");
	}else{
		if(acesso.tipo == 'aluno'){
		    document.querySelector("#cadastrar_aula").remove();
		}else{
			document.querySelector("#listProf").remove();
		}

	}
});

document.querySelector("#user-input").addEventListener('change', () => {
  let heigthInput = document.querySelector("#user-input").offsetHeight
  let x = document.querySelector("body > div.write-bar > button");
  x.style.height = heigthInput + 'px';
})
let heigthInput = document.querySelector("#user-input").offsetHeight
let x = document.querySelector("body > div.write-bar > button");
x.style.height = heigthInput + 'px';