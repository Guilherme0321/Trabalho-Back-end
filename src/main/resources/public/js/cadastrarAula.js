let acesso = localStorage.getItem('acesso');
acesso = JSON.parse(acesso);

if ((acesso == null || acesso.id == null) && !window.location.href.endsWith("login.html")) {
	window.open("login.html", "_self");
}else{
	if(acesso.tipo == 'aluno'){
    document.querySelector("#cadastrar_aula").remove();
}
}