const express = require('express');
const { networkInterfaces } = require('os');

const nets = networkInterfaces();
const results = []; //

for (const name of Object.keys(nets)) {
  for (const net of nets[name]) {
    if (net.family === 'IPv4' && !net.internal) {
      results.push(net.address);
    }
  }
}

const port = process.env.PORT || 3000;
const host = process.env.PORT || 
              ((results && results.length > 0) ? results[0] : null) || 
              'localhost';

let server = express()

server.use(express.static(__dirname + '/dist/'))

server.get('/*', function(req, res) {
  res.sendfile(__dirname + '/dist/index.html');
});

server.listen(port, host,(err) => {
  if (err) {
    console.error(err);
  } else {
    console.log(`Server is listening at http://${host}:${port}`);
  }
})
