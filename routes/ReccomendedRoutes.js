const express = require('express');
const { default: axios } = require('axios');
const os = require('os');
const pool = require('../db');

const mapboxApiKey = 'pk.eyJ1IjoiaGFtbWFkcXNyMjk2NCIsImEiOiJjbGhsMWhlMWgwNHExM2RwOXFxZmVoMTc0In0.89FEG2rDAJpCL1H3ShfP_w';
const opentripmapsApikey = '5ae2e3f221c38a28845f05b696e2158c125a0f6b9d9200a83cf9674f';

const RecRouter = express.Router();

// const ipAddress = getIPAddress();

// function getIPAddress() {
//   const networkInterfaces = os.networkInterfaces();
//   const wifiInterface = networkInterfaces['Local Area Connection* 10'] || networkInterfaces['Ethernet']; // Adjust the interface name as per your system

//   if (!wifiInterface) {
//     return 'Unknown';
//   }

//   const wifiAddress = wifiInterface.find(({ family }) => family === 'IPv4');
//   return wifiAddress ? wifiAddress.address : 'Unknown';
// }

// RecRouter.get('/city', (req,res)=> {

//   axios.get(`https://api.mapbox.com/geocoding/v5/mapbox.places/${ipAddress}.json?access_token=${mapboxApiKey}`)
//   .then(response => {
//     const features = response.data.features;

//     console.log(features);

//     const city = features.length > 0? features[0].text : 'Unknown0';

//     res.json({city});
//     console.log('ip is : ', ipAddress);
//   })
//   .catch(error => {
//     console.error('Error retrieving geolocation:', error);
//     res.status(500).json({ error: 'Failed to retrieve geolocation data' });
//   });
// });

RecRouter.get('/places', (req, res) => {
  const { city } = req.query;

  axios
    .get(`https://api.opentripmap.com/0.1/en/places/geoname?name=${city}&apikey=${opentripmapsApikey}`)
    .then((response) => {
      const { lat, lon } = response.data;
      const kinds = 'historic%2Ctourist_facilities%2Creligion%2Cview_points%2Cnatural%2Ccultural';
      return axios.get(`https://api.opentripmap.com/0.1/en/places/radius?radius=1000&lon=${lon}&lat=${lat}&kinds=${kinds}&apikey=${opentripmapsApikey}`);
      })
    .then((response) => {
      // const { data } = response;
      // const places = data.features.map(feature => {
      //   const name = `Name: ${feature.properties.name}`;
      //   const xid = `XID: ${feature.properties.xid}`;
      //   const address = `Address: ${feature.properties.address}`;
      //   return [name, xid, address];
      // });
      // res.json({ places });
      const { data } = response;
      const places = data.features.map(feature => ({
        name: feature.properties.name,
        // xid: feature.properties.xid,
        kinds: feature.properties.kinds,  
      }));
      res.json({ places });
    })
    .catch((error) => {
      console.error('Error retrieving places:', error);
      res.status(500).json({ error: 'Failed to retrieve places' });
    });
});

module.exports = RecRouter;
