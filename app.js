let express = require('express');
let app = express();

const bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());
app.use(express.urlencoded());

app.get('/', function (req, res) {
    res.send('Hello World! cai ma cha may');
});
app.listen(process.env.PORT || 3000, function () {
    console.log('Example app listening on port 3000!');
});