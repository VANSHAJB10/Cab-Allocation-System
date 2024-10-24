const socker = io(); // connection req to server from socket.io

// if browser supports geolocation then 
if(navigator.geolocation){
    navigator.geolocation.watchPosition(
        (position) =>{
            const{latitude,longitude} = position.coords;
            socket.emit("send-location", {latitude,longitude});
        }, 
        (error) => {
            console.log(error);
        },
        // further settings for recording location 
        {
            enableHighAccuracy: true,
            timeout: 5000, //capture location again after 5 sec
            maximumAge: 0 // no cached location data to be taken 
        }
    );

}

L.map("map");
