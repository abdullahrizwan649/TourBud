const { Pool } = require('pg');

const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'post_mid',
  password: 'pgadmin',
  port: 5432,
});

pool
  .connect()
  .then(() => {
    console.log('Database connected successfully!');
  })
  .catch((error) => {
    console.error('Error connecting to the database:', error);
  });

console.log("Database ready");

module.exports = pool;

