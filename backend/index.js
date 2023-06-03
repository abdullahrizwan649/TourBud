const express = require('express');
const bodyParser = require('body-parser');


var cors = require('cors');
const app = express();
const recRoutes = require('./routes/ReccomendedRoutes.js');
const user_router = require('./routes/userRoutes.js');
const tour_router = require('./routes/tourRoutes.js');
const activity_router = require('./routes/activityRoutes.js');
const port = 3000;

app.use(express.json());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors());

app.use('/users', user_router);
app.use('/tours', tour_router);
app.use('/activity', activity_router);
app.use('/reccommend', recRoutes);

app.listen(port, ()=> 
{
    console.log('Server is running on port: ',port);
});

