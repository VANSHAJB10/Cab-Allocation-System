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


const map = L.map("map").setView([0,0], 15);

L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "Map Source: OpenStreetMap"

}).addTo(map);

const markers= {};

function sendLocation(latitude, longitude) {
    socket.emit("send-location", { latitude, longitude });
}

socket.on("recieve-location", (data) => {
    const{id, latitude, longitude} = data;

    // Center the map view on the latest coordinates
    map.setView([latitude,longitude]);

    if (markers[id]) {
        markers[id].setLatLng([latitude, longitude]);
    } 
    else {
        // Add a new marker for the user
        markers[id] = L.marker([latitude, longitude]).addTo(map);
    }
});

socket.on("user-disconneted", (id)=>{
    if(markers[id]){
        map.removeLayer(markers[id]);
        delete markers[id];
    }
});

