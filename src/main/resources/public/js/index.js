document.addEventListener("DOMContentLoaded", function() {
  let acesso = localStorage.getItem('acesso');
  acesso = JSON.parse(acesso);

  if (acesso == null && !window.location.href.endsWith("login.html")) {
    window.open("login.html", "_self");
  }
});