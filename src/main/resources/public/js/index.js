let acesso = localStorage.getItem('acesso');
acesso = JSON.parse(acesso);

if ((acesso == null || acesso.id == null) && !window.location.href.endsWith("login.html")) {
	window.open("login.html", "_self");
}else{
	if(acesso.tipo == 'aluno'){
    	document.querySelector("#cadastrar_aula").remove();
    	getReservas('aluno');
	}else{
		document.querySelector("#listProf").remove();
		getReservas('professor');
	}
}
let x;

async function getReservas(tipo){
	fetch(`/reservas?id=${acesso.id}&tipo=${acesso.tipo}`)
	.then(function(res){
		return res.json();
	})
	.then(function(response){
		adicionarTablea(response);
	})
	.catch(function(error){
		console.error(error);
	})
}

function recusar(row, resposta){
	var tr = row.closest('tr');
	let id = tr.querySelector('td:nth-child(1)').textContent;
	let id_aula = tr.querySelector('td:nth-child(3)').textContent;
	
	let reserva = {
		"id":id,
		"id_aula": id_aula,
		"status": resposta
	}
	console.log(reserva);
	tr.remove();
	enviarStatus(reserva);
}

async function enviarStatus(status){
	const request = {
	    method: 'POST',
	    headers: {
	      'Content-Type': 'application/json',
	    },
	    body: JSON.stringify(status)
    };
    
    try {
	  	const response = await fetch("/updateStatus",request);  	
    	
    if (!response.ok) {
      	throw new Error('Resposta não está ok!');
    }
    
    const responseData = await response.json();
    console.log(responseData.response);
    
    console.log('POST request successful. Response data:', responseData);
  } catch (error) {
    	console.error('Error:', error);
  }
  
}

function adicionarTablea(data){
	data.forEach( data => {
    let row =  `<tr>
                    <td>${data.id}</td>
                    <td>${data.id_aluno}</td>
                    <td>${data.id_aula}</td>
                    <td>${data.data}</td>
                    <td>${data.status}</td>
                    <td>
                    	<img src="./images/checked-list.png" onclick="recusar(this,'confirmada')" class="tableIcon" alt="Aceitar">
                   		<img src="./images/notcheck.png" onclick="recusar(this,'cancelada')" class="tableIcon" alt="Recusar">
                   	</td>
                </tr>`;
    document.querySelector("#lista-ativ").innerHTML += row;
})
}