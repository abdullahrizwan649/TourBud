const express = require('express');
const moment = require('moment');
const pool = require('../db');
const tour_router = express.Router();

tour_router.get('/', async(req,res)=> {
    try {
        const { rows } = await pool.query('SELECT * FROM tours');
        // const newdata = rows.map ((row)=> ({
        //     tourId: row.tourId,
        //     origin: row.origin,
        //     destination: row.destination,
        //     departureDate: row.departureDate,
        //     returnDate: row.returnDate,
        //     cost: row.cost,
        //     noOfPeople: row.noOfPeople,
        //     tourProvider: row.tourProvider,
        //     tourProviderContact: row.tourProviderContact,
        //     details: row.details,
        // }));
        res.json(rows);
        console.log('Successfully retrieved');
      } catch (error) {
        console.error('Error retrieving users:', error);
        res.status(500).json({ error: 'Error retrieving tours' });
      }
});

tour_router.get('/search', (req, res) => {
    const { origin, destination, departureDate, returnDate, noofpeople } = req.body;

    let query = 'SELECT origin, destination, departureDate, returnDate, noOfPeople, cost, tourProvider, details FROM tours WHERE 1=1';

    if (origin) {
        query += ` AND origin = '${origin}'`;
    }

    if (destination) {
        query += ` AND destination = '${destination}'`;
    }

    if (departureDate) {
        query += ` AND departuredate = '${departureDate}'`;
    }

    if (returnDate) {
        query += ` AND returndate = '${returnDate}'`;
    }

    if (noofpeople) {
        query += ` AND noofpeople = ${noofpeople}`;
    }

    pool.query(query, (err, result) => {
        if (err) {
        console.error(err);
        return res.status(500).json({ error: 'Error getting data' });
        }

        if (result.rows.length > 0) {
        const formattedRows = result.rows.map((row) => ({
            ...row,
            departuredate: moment.utc(row.departuredate).local().format(),
            returndate: moment.utc(row.returndate).local().format(),
        }));

        res.json(formattedRows);
        } else {
        return res.status(500).json({ error: 'No data matching the search criteria' });
        }
    });
});

tour_router.get('/book', (req, res) => {
    const userId = req.body.userid;
    const tourId = req.body.tourId;
    pool.query(
      'SELECT * FROM activity WHERE userid = $1 AND tourId = $2',
      [userId, tourId],
      (err, result) => {
        if (err) {
          console.error(err);
          return res.status(500).json({ inserted: false, error: 'Failed to check activity' });
        }
  
        if (result.rows.length > 0) {
          return res.status(400).json({ inserted: false, error: 'Tour already booked' });
        }
        pool.query(
            'select tourProvider, tourProviderContact from tours where tourId = $1',
            [tourId],
            (err, result) => {
                if (err) {
                  console.error(err);
                  return res.status(500).json({ inserted: false, error: 'Failed to add activity' });
                }
                const tourDetails = result.rows[0];

                pool.query(
                    'INSERT INTO activity (userid, tourId, times) VALUES ($1, $2, CURRENT_TIMESTAMP)',
                    [userId, tourId],
                    (err, result) => {
                      if (err) {
                        console.error(err);
                        return res.status(500).json({ inserted: false, error: 'Failed to add activity' });
                      }
                      res.send({ inserted: true, tourDetails });
                    }
                  );
              }
        );
      }
    );
});

module.exports = tour_router;