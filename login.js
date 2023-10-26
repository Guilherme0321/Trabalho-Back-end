
let user = document.querySelector("#nome").value;
let senha = document.querySelector("#senha").value;
document.querySelector("#nome").addEventListener("submit", () => {
    let url = `/user?nome=${encodeURIComponent(user)}&senha=${encodeURIComponent(senha)}`
    fetch(url)
    .then(function(response) {
        return response.text();
        })
    .then(function(data) {
        console.log(data);
    })
    .catch(function(error) {
        console.error("Erro ao fazer login:", error);
    });
});
