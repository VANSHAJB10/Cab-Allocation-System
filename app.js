const express = require('express');
const app = express();

// handling errors of strict MIME checking in browser
app.use(express.static('public')); 

const path = require("path");

const http = require("http");// package in node
const socketio = require("socket.io");
const server = http.createServer(app);

const io = socketio(server);

app.set("view engine", "ejs");
app.set(express.static(path.join(__dirname, "public")));

//handling connection request from socket.io
io.on("connection", function(socket){
    socket.on("send-location", function(data){
    io.emit("recieve-location",{id: socket.id, ...data});
    });

    socket.on("disconnet", function(){
    io.emit("user-disconnected", socket.id);
    });
    
    console.log("connected");
});

app.get("/", function(req,res){
    res.render("index");
});

server.listen(3000);
