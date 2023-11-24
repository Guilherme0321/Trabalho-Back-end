function saveProfile() {

  var nome = document.getElementById('nome').value;
  var email = document.getElementById('email').value;
  var tipoUsuario = document.getElementById('tipo_usuario').value;
  var sobreMim = document.getElementById('sobre_mim').value;

  console.log('Nome:', nome);
  console.log('Email:', email);
  console.log('Tipo de Usuario:', tipoUsuario);
  console.log('Sobre Mim:', sobreMim);


  // Perfil.js

function saveProfile() {
  // Obter os valores dos campos do formulário
  const nome = document.getElementById('nome').value;
  const email = document.getElementById('email').value;
  const tipoUsuario = document.getElementById('tipo_usuario').value;
  const sobreMim = document.getElementById('sobre_mim').value;

  // Criar um objeto com os dados do formulário
  const profileData = {
      nome,
      email,
      tipoUsuario,
      sobreMim,
  };

  // Enviar os dados para o back-end usando a API Fetch
  fetch('/Perfil', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
      },
      body: JSON.stringify(profileData),
  })
  .then(response => {
      if (!response.ok) {
          throw new Error('Erro ao salvar perfil');
      }
      return response.json();
  })
  .then(data => {
      console.log('Perfil salvo com sucesso:', data);
      // Adicione aqui qualquer lógica adicional após o salvamento bem-sucedido
  })
  .catch(error => {
      console.error('Erro ao salvar perfil:', error);
      // Adicione aqui qualquer lógica adicional para lidar com erros
  });
}

// Adicionar um ouvinte de evento ao botão Salvar
document.getElementById('saveButton').addEventListener('click', saveProfile);

}