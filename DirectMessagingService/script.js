

const socket = io('http://localhost:3000', { autoConnect: false })
const messageContainer = document.getElementById('message-container')
const messageForm = document.getElementById('send-container')
const messageInput = document.getElementById('message-input')


  
  socket.connect();
  const name = prompt('What is your name?')
     appendMessage('You joined')
    
    
    
     const userIDS={userTrigger_ID:"rnd Id1",user_ID2:"rnd Id2"};
     getIdFromToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbWluZSIsImlhdCI6MTY0MDg4MjE0MywiZXhwIjoxNjQwOTY4NTQzfQ.dj8ZAmqtq8aV3k8jvGGobsGHTlNcydPAkjQakNXo_6j8XD5iu8fGYghktQfKtFux6x_Vv3vPT0xq0iZvvJ6xrQ").then(
       res => {
        console.log(res)
      // userIDS.userTrigger_ID=res;
      } 
     ); 
   
    socket.emit('new-user', name)
    socket.emit('new-connection',userIDS )
  

  var roomName;
  var usersConnected=[];
  //when the user get connected ,all messages get retreived from  mongo 
  socket.on('stored-messages', messages => console.log(messages))
  socket.on('roomName', rName => {
    roomName=rName;
  })
  socket.on('connected-users', namesList => {
    console.log("---------uses-----");
    console.log(namesList);
    usersConnected=namesList;
  })

//listen to private messages
socket.on('private message', (content,userID) =>   {
  console.log("--------senderID--1--");
  console.log(userID);
  if(userID!=userIDS.userTrigger_ID) appendMessage(`${OtherName}: ${content}`)
}  )
// const name = prompt('What is your name?')
// appendMessage('You joined')
// socket.emit('new-user', name)

// socket.on('chat-message', data => {
//   appendMessage(`${data.name}: ${data.message}`)
// })

// socket.emit("private message", {
//   content:"rnd content",
//   to: "USER_CONST_ID",
// });
// ///
// socket.on("private message", ({ content, from }) => {
//   alert(content);
//   alert(from);
// });
// ////
var OtherName="Other";
socket.on('user-connected', name => {
  OtherName=name;
  appendMessage(`${name} connected`)
})

// socket.on('user-disconnected', name => {
//   appendMessage(`${name} disconnected`)
// })

messageForm.addEventListener('submit', e => {
  e.preventDefault()
  const message = messageInput.value
  appendMessage(`You: ${message}`)
  //socket.emit('send-chat-message', message)
  socket.emit("private message", {
    content: message,
    roomName: roomName,
  })
  messageInput.value = ''
})

function appendMessage(message) {
  const messageElement = document.createElement('div')
  messageElement.innerText = message
  messageContainer.append(messageElement)
}


async function getIdFromToken(TriggerUserToken){

  const response = await  fetch("http://10.1.8.149:8080/api/auth/token="+TriggerUserToken, {
    method: 'GET', // *GET, POST, PUT, DELETE, etc.
  });
    const res=await response.json();
  return res; // parses JSON response into native JavaScript objects
}