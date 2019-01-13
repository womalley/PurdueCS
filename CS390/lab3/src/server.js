const express = require('express');
const bodyParser = require('body-parser');
const app = express();
let itemArr = new Array();
let idCounter = 0;

app.use(express.static('./'));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

//GET /
app.get('/', (req, res) => {
    res.sendFile('index.html');
});

//GET /items
app.get('/items', (req, res) => {
    if (req.headers.authorization !== 'null') {
        res.json(itemArr);
        res.status(200).send({ message: 'OK' });
    }
    else {
        res.status(401).send({ message: 'Authorization: no authorization' });
    }
});

//GET /items/search
app.get('/items/search', (req, res) => {
    if (req.headers.authorization !== 'null') {
        let search = req.query.search;
        let price = req.query.price;
        let rating = req.query.rating;

        let searchRes = filter(price, rating, search);

        res.json(searchRes);
        res.status(200).send({ message: 'OK' });
    }
    else {
        res.status(401).send({ message: 'Authorization: no authorization' });
    }
});

//GET /items/<id>
app.get('/items/:id', (req, res) => {
    if (req.headers.authorization != 'null') {
        let id = req.params.id;
        for (let i = 0; i < itemArr.length; i++) {
            if (itemArr[i].id === parseInt(id, 10)) {
                res.json(itemArr[i]);
                res.status(200).send({ message: 'OK' });
            }
        }

        let item = new itemList("Item doesn't exist", "not in array list", 1, 1, -1);
        res.json(item);
        res.status(200).send({ message: 'This item does not exist'});
    }
    else {
        res.status(401).send({ message: 'Authorization: no authorization' });
    }
});

//POST /new-item
app.post('/new-item', (req, res) => {

    if (req.headers.authorization === 'null') {
        res.status(401).send({ message: 'Authorization: no authorization' });
    }
    else {
        //400 empty/null string
        if (req.body.name.length === 0 || req.body.description.length === 0 || req.body.price.length === 0 || req.body.rating.length === 0) {
            res.status(400).send({ message: 'Bad request: empty input' });
            return;
        }
        //400 invalid length
        else if (req.body.name.length < 3 || req.body.name.length > 40 || req.body.description.length < 3 || req.body.description.length > 80) {
            res.status(400).send({ message: 'Bad request: invalid length' });
            return;
        }
        else if (req.body.rating < 1 || req.body.rating > 5) {
            res.status(400).send({ message: 'Bad request: rating should be between 1 and 5'});
            return;
        }
        else if (req.body.price < 0.1) {
            res.status(400).send({ message: 'Bad request: price should be 0.10 or more'});
            return;
        }

        //check to see if item name already exists
        for (let i = 0; i < itemArr.length; i++) {
            if (itemArr[i].name === (req.body.name)) {
                res.status(400).send({ message: 'Bad request: already includes that name' });
                return;
            }
        }

        let newItem = new itemList(req.body.name, req.body.description, req.body.price, req.body.rating, idCounter++);
        itemArr.push(newItem);
        res.status(200).send({ message: 'OK' });
    }
});

//POST /login
app.post('/login', (req, res) => {
    let username = req.body.username;
    let password = req.body.password;

    //400 empty/null string
    if (username.length === 0 || password.length === 0) {
        res.status(400).send({ message: 'Bad request: empty input' });
    }
    //400 invalid length
    else if (username.length < 8 || password.length < 8 || username.length > 16 || password.length > 16) {
        res.status(400).send({ message: 'Bad request: invalid length' });
    }
    //400 invalid character
//    else if (/^[a-zA-Z0-9]*$/.test(username) === false || /^[a-zA-Z0-9]*$/.test(password) === false) {
//        res.status(400).send({ message: 'Bad request: invalid special character' });
//    }

    //401 improper login credentials
    if (username !== "cs390wap" || password !== "cs390wap") {
        res.status(401).send({ message: "Unauthorized: improper login credentials" });
    }

    let token = makeToken();
    res.set('Set-Authorization', token);
//    console.log("TOK: " + token);
    res.status(200).send({ message: 'OK' });
//    console.log("User: " + username + "  Pass: " + password);
});

//GET /login
app.get('/login', (req, res) => {
    res.status(200).sendFile('/login/login.html', { root: './' });
});

//GET /item-detail/<id>
app.get('/item-detail/:id', (req, res) => {
    res.status(200).sendFile('/item-detail/item-detail.html', { root: './' });
});


app.listen(8650, () => console.log("listening on port 8650"));

//Create item object
class itemList {
    constructor(name, description, price, rating, id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.id = id;
    }
}

//Token generator
function makeToken() {
    let str = "";
    let characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (let i = 0; i < 20; i++)
        str += characters.charAt(Math.floor(Math.random() * characters.length));

    return str;
}

//Filter
function filter(price, rating, search) {
    let tmpItems = new Array();

    let index = 0;
    while (index < itemArr.length) {

        if (price === 'all') {
            if (itemArr[index].rating === rating) {
                if (search.length > 0) {
                    if (itemArr[index].name.indexOf(search) != -1) {
                        tmpItems.push(itemArr[index]);
                    }
                }
                //no search parameters
                else {
                    tmpItems.push(itemArr[index]);
                }
            }
        }
        else if (price === 'low') {
            if (itemArr[index].rating === rating && (parseInt(itemArr[index].price, 10) < 20)) { 
                if (search.length > 0) {
                    if (itemArr[index].name.indexOf(search) != -1) {
                        tmpItems.push(itemArr[index]);
                    }
                }
                //no search parameters
                else {
                    tmpItems.push(itemArr[index]);
                }
            }
        }        
        else if (price === 'mid') {
            if (itemArr[index].rating === rating && ((parseInt(itemArr[index].price, 10) >= 20) && (parseInt(itemArr[index].price, 10) <= 40))) { 
                if (search.length > 0) {
                    if (itemArr[index].name.indexOf(search) != -1) {
                        tmpItems.push(itemArr[index]);
                    }
                }
                //no search parameters
                else {
                    tmpItems.push(itemArr[index]);
                }
            }
        }        
        else if (price === 'high') {
            if (itemArr[index].rating === rating && (parseInt(itemArr[index].price, 10) > 40)) { 
                if (search.length > 0) {
                    if (itemArr[index].name.indexOf(search) != -1) {
                        tmpItems.push(itemArr[index]);
                    }
                }
                //no search parameters
                else {
                    tmpItems.push(itemArr[index]);
                }
            }
        }

//        console.log(tmpItems);
        index++;
    }

    return (tmpItems);
}

