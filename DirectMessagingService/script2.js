const socket = io('http://localhost:3000', { autoConnect: false })
const messageContainer = document.getElementById('message-container')
const messageForm = document.getElementById('send-container')
const messageInput = document.getElementById('message-input')


  
  socket.connect();
  const name = prompt('What is your name?')
  appendMessage('You joined') 
  
  const userIDS={userTrigger_ID:"rnd Id2",user_ID2:"rnd Id1"};
  socket.emit('new-user', name)
  socket.emit('new-connection',userIDS )

  var roomName;
  var usersConnected=[];
  //when the user get connected ,all messages get retreived from  mongo 
  socket.on('stored-messages', messages => console.log(messages))
  socket.on('roomName', rName => {
   
    roomName=rName;
  })

  var OtherName="Other";
  socket.on('connected-users', namesList => {
    console.log("---------uses-----");
    console.log(namesList);
    usersConnected=namesList;
    OtherName=usersConnected.filter(n => n!==name )[0];
  })

//listen to private messages
//listen to private messages
socket.on('private message', (content,userID) =>   {
  console.log("--------senderID--2--");
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