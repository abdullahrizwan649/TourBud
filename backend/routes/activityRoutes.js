const express = require('express');
const pool = require('../db');
const activity_router = express.Router();

activity_router.get('/', (req,res)=>{
    const userid = req.body.userid;

    pool.query(
        'select * from tours where tourId in (select tourId from activity where userid = $1)',
        [userid],
        (err,result) => {
            if (err) {
                console.error(err);
                return res.status(500).json({ error: 'Failed to retrieve activity' });        
            }

            res.send(result.rows);
    });
});

activity_router.get('/delete', (req,res)=>{
    const userid = req.body.userid;
    const tourId = req.body.tourId;
    pool.query(
        'delete from activity where tourId = $1 and userid = $2',
        [tourId, userid],
        (err,result) => {
            if (err) {
                console.error(err);
                return res.status(500).json({ "deleted": false });        
            }

            res.send({"deleted": true});
    });
});

module.exports = activity_router;