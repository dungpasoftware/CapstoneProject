const express = require('express');

const port = 3000;

let server = express()

server.use(express.static(__dirname + '/dist/'))

server.get('/*', function(req, res) {
  res.sendfile(__dirname + '/dist/index.html');
});

server.listen(process.env.PORT || port, (err) => {
  if (err) {
    console.error(err);
  } else {
    console.log(`Server is listening at ${port}`);
  }
})
