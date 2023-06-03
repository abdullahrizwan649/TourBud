const express = require('express');
const pool = require('../db');

const user_router = express.Router();

// user_router.get('/', async (req, res) => {
//     try {
//       const { rows } = await pool.query('SELECT * FROM users');
//       const newdata = rows.map ((row)=> ({
//           userid: row.userid,
//           name: row.name,
//           cnic: row.cnic,
//           phone: row.phone,
//           password: row.password,
//           email: row.email
//       }));
//       res.json(newdata);
//       console.log('Successfully retrieved');
//     } catch (error) {
//       console.error('Error retrieving users:', error);
//       res.status(500).json({ error: 'Internal Server Error' });
//     }
// });
  
user_router.post('/register', (req, res) => {
  // const userid = req.body.userid;
  const password = req.body.password;
  const name = req.body.name;
  const cnic = req.body.cnic;
  const phone = req.body.phone;
  const email = req.body.email;

  pool.query(
    'INSERT INTO users (password, name, cnic, phone, email) VALUES ($1, $2, $3, $4, $5)',

    [password, name, cnic, phone, email],
    (err, results) => {
      if (err) {
        console.error(err);
        return res.status(500).json({ error: 'Failed to register user' });
      }

      res.send({"inserted": true});
    }
  );
});

user_router.post('/login', (req, res) => {
  const email = req.body.email;
  const password = req.body.password;
  console.log('email:', email); // Add this line to check the value
  console.log('password:', password); 

  pool.query(
    'SELECT * FROM users WHERE email = $1',
    [String(email)],
    (err, results) => {
      if (err) {
        console.error(err);
        return res.status(500).json({ error: 'Failed to login user' });
      }
  //console.log('password:', results.rows[0].password); 

      if (results.rows.length > 0) {
        if (password == results.rows[0].passsword) {
          res.json({ loggedIn: true, email: email, message: 'logged in' });
        } else {
          res.json({ loggedIn: false, message: 'username or password incorrect' });
        }
        console.log('logged into user: ', email);
      } else {
        res.json({ message: 'user does not exist' });
      }
    }
  );
});

user_router.get('/:id', (req, res) => {
    const userId = req.params.id;

    pool.query('SELECT * FROM users WHERE userId = $1', [userId], (error, result) => {
        if (error) {
        console.error('Error retrieving user information:', error);
        return res.status(500).json({ error: 'Failed to retrieve user information' });
        }
        const user = result.rows[0]; 
        if (!user) {
        return res.status(404).json({ error: 'User not found' });
        }
        res.json({ user });
    });
});

user_router.get('/signout', (req,res)=>{
  // const userid = req.body.userid;
  res.send({"loggedIn": false, "userid": ""});
});


module.exports = user_router;
