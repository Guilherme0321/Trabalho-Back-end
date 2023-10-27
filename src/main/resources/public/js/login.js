
document.querySelector("#form").addEventListener("submit", (e) => {
	e.preventDefault();
	let user = document.querySelector("#nome").value;
	let senha = document.querySelector("#senha").value;
    let url = `/user?email=${encodeURIComponent(user)}&senha=${encodeURIComponent(senha)}`
    fetch(url)
    .then(function(response) {
        return response.text();
        })
    .then(function(data) {
		if(data == null){						
	        localStorage.setItem('acesso', "");
		}else{			
        	localStorage.setItem('acesso', data);
		}
		document.querySelector("#form").submit();
    })
    .catch(function(error) {
        console.error("Erro ao fazer login:", error);
    });
    
});
