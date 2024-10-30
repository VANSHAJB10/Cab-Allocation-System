# Phase 1-->
## Real-Time Location Tracking using Socket.io 


This Project uses OpenStreet Map for displaying the Current Location of user on the world map.
```
All the International Borders are displayed by OpenStreetMap.
The developer of this project has no intention to represent wrong borders of any State/Country.
```

### Install required packages:
```
npm i express ejs
```

```
npm i socket.io
```

### To run the server
```
npx nodemon app.js
```
<p>The server will run on localhost:PORT  (PORT to be defined | 3000 by default in code)</p>


#### Features:
<ul>
  <li>Real Time Location of all active users is displayed using Markers on the Map</li>
  <li>Location is served with HighAccuracy and No Cached Data is served</li>
  <li>Location is updated every 5 seconds. </li>
  <li>Map view centered with respect to user's latest coordinates</li>
  <li>Broadcasting User Locations of all active users to all the connections</li>
</ul>

