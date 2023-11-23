let acesso = localStorage.getItem('acesso');
acesso = JSON.parse(acesso);

if ((acesso == null || acesso.id == null) && !window.location.href.endsWith("login.html")) {
	window.open("login.html", "_self");
}else{
	if(acesso.tipo == 'aluno'){
    document.querySelector("#cadastrar_aula").remove();
}
}

let form = document.querySelector("#form");

let online = document.querySelector("#Online");
let presencial =  document.querySelector("#Presencial");

let local = "";

online.addEventListener("click", () => {
	local = online.value;
	document.querySelectorAll('.endereco').forEach(x => {
		x.style.display = 'none';
	})
		document.querySelector("#cidade").required = false;
		document.querySelector("#bairro").required = false;
		document.querySelector("#rua").required = false;
		document.querySelector("#numero").required = false;
		document.querySelector("#complemento").required = false;
		document.getElementById('link-aula').style.display = 'flex';
		document.getElementById('link').required = true;
});
presencial.addEventListener("click", () => {
	local = presencial.value;
	document.querySelectorAll('.endereco').forEach(x => {
		x.style.display = 'flex';
	})
	document.querySelector("#cidade").required = true;
	document.querySelector("#bairro").required = true;
	document.querySelector("#rua").required = true;
	document.querySelector("#numero").required = true;
	document.querySelector("#complemento").required = true;
	document.getElementById('link-aula').style.display = 'none';
	document.getElementById('link').required = false;
});

form.addEventListener('submit', () => {
	let titulo = document.querySelector("#Titulo").value
	let descricao = document.querySelector("#Descricao").value
	let preco = document.querySelector("#Preco").value
	let data = document.querySelector("#data").value
	let endereco = '';
	
	if(local == 'online'){
		endereco = document.querySelector("#link").value;
	}else if(local == 'presencial'){
		let cidade = document.querySelector("#cidade").value;
		let bairro = document.querySelector("#bairro").value;
		let rua = document.querySelector("#rua").value;
		let num = document.querySelector("#numero").value;
		let complemento = document.querySelector("#complemento").value;
		endereco = `${cidade}-${bairro}-${rua}-${num}-${complemento}`;
	}
	let aula = {
		'professor_id': acesso.id,
		'titulo': titulo,
		'descricao': descricao,
		'preco': preco,
		'data': data,
		'tipo': local,
		'local': endereco
	}

	const configuretions = {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(aula)
	}
	sendAula(configuretions);
})

async function sendAula(aula){
	const res = await fetch('/aula',aula);

	if(!res.ok){
		throw new Error("Não foi enviado corretamente!");
	}

	const response = await res.json();
	console.log(response);
}



