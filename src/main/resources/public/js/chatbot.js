const chatWindow = document.getElementById('chat-window');
    const userInput = document.getElementById('user-input');
    const input_user = document.getElementById('user-input');

    input_user.addEventListener("keydown", (e) => {
      if(e.keyCode === 13){
        sendMessage();
      }
    })

    function sendMessage() {
      const message = userInput.value;
      chatWindow.innerHTML += `<p>Você: ${message}</p>`;
      userInput.value = '';

      setTimeout(() => {
        chatWindow.innerHTML += `<p>Chat bot: ${message}</p>`;
      }, 1000);
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