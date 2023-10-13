document.querySelector("#form").addEventListener('submit', (e) => {
  let name = document.querySelector("#nome_completo");
  let email = document.querySelector("#Email").value;
  let senha = document.querySelector("#Senha").value;

  if (/^[a-zA-Z]+$/.test(name.value)) {
    var xml = new XMLHttpRequest();

    xml.open("POST", "/usuario", true);
    xml.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xml.send(`nome=${encodeURIComponent(name.value)}&email=${encodeURIComponent(email)}&senha=${encodeURIComponent(senha)}`);
  } else {
    name.placeholder = 'Nome Invalido!';
    name.value = '';
  }
});