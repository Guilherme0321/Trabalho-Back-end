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