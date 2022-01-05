//const io = require('socket.io')(3000)
const app = require('express')();
const server = require('http').Server(app);
var bodyParser = require('body-parser');
const io = require('socket.io')(server);
//database settings
var MongoClient = require('mongodb').MongoClient;
var databaseName="DMS_DB"
var url = "mongodb://localhost:27017/"+databaseName;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

server.listen(3000, () => {
  console.log('listening on *:3000');
});
/**---------------------SERVER LOGIC---------------------------- */

app.post('/createRoom',(request,response)=>{

   let id1=request.body.userID_1;
   let id2=request.body.userID_2;
   let roomName=id1+id2;
   console.log(id1);
   createRoom(id1,id2,roomName);

   response.send('This is Direct Messages Service, room Created').status(200);
})

function createRoom(id1,id2,roomName){
 //created the room Object
 let room={
  userID_1:id1,
  userID_2:id2,
  roomName:roomName,
  messages:[]
}
//Connect to mongo
MongoClient.connect(url,(err,db)=>{

 if(err) throw err;
 console.log("Mongodb connected.....");

 let dbo = db.db(databaseName);     
 let rooms =dbo.collection('rooms');
 rooms.insertOne(room);

} )
}

/**----------------SOCKET LOGIC------------------------- */


startMessaging(url);

var NamesList=[];

function startMessaging(url) {

  //Connect to mongo 
  MongoClient.connect(url,(err,db)=>{
      
    if(err) throw err;
    console.log("Mongodb connected.....");
  
    //after the connection to the db is established let's connect tO Socket io
    io.on('connection',(socket)=>{
        console.log("new Connection!!!!");
       let dbo = db.db(databaseName);     
       let rooms =dbo.collection('rooms');
            
       socket.on('new-user', name => {
        //users[socket.id] = name

        socket.broadcast.emit('user-connected', name)
        NamesList.push(name);
        console.log(NamesList);
      }) 
       socket.on("new-connection",({userTrigger_ID,user_ID2})=>{
                 let id1=userTrigger_ID;
                 let id2=user_ID2;
                  //i should get the right room where the disscusion is  stored
                  
          rooms.find({ $or: [ { user_ID1: { $eq: id1 },user_ID2: { $eq: id2 } }, { user_ID1: { $eq: id2 },user_ID2: { $eq: id1 } } ]}).toArray( (err,res)=>{  
            if( err ) throw err;
            let roomName=res[0].roomName;
            socket.userID=userTrigger_ID;
            socket.join(roomName);
            io.to(socket.id).emit('stored-messages',res[0].messages);
            io.to(socket.id).emit('roomName',roomName);
            io.to(socket.id).emit('connected-users',NamesList);
            io.to(roomName).emit('connected-users', NamesList );
         } ) 
       })


       socket.on("private message",({content,roomName})=>{
         console.log("-------privte Message Sections--------");
         console.log(socket.userID);
         console.log(content);
         io.to(roomName).emit('private message', content, socket.userID );
         saveMessage(content,roomName,socket);
        
       })  
    });
  });
}
  function saveMessage(content,roomName,socket){
    
    MongoClient.connect(url, function(err, db) {
      if (err) throw err;
      var dbo = db.db(databaseName);
      var myquery = { roomName: roomName };
      var newvalue = { senderID:socket.userID,content:content };
      
      dbo.collection('rooms').updateOne(
        myquery,
        { $push: { messages: newvalue } }
     )
    });
  }

// io.on('connection', socket => {
//   socket.on('new-user', name => {
//     console.log("user jdid: "+ name);
//     users[socket.id] = name
//     socket.broadcast.emit('user-connected', name)
//   })
//   socket.on("private message", ({ content, to }) => {
//     socket.to(to).emit("private message", {
//       content,
//       from: socket.id,
//     });
//   });
//   socket.on('send-chat-message', message => {
//     socket.broadcast.emit('chat-message', { message: message, name: users[socket.id] })
//   })
//   socket.on('disconnect', () => {
//     socket.broadcast.emit('user-disconnected', users[socket.id])
//     delete users[socket.id]
//   })
// })