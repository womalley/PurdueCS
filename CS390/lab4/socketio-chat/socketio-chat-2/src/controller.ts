import * as io from 'socket.io-client';
import 'materialize-css/dist/css/materialize.css';

let socket = io();
let containerDiv = document.getElementById('container');
let usernameBtn = document.getElementById('usernameBtn');
let usernameInput: HTMLInputElement = <HTMLInputElement>document.getElementById('username');
let messageInput: HTMLInputElement = <HTMLInputElement>document.getElementById('message');
let messageBtn = document.getElementById('sendMessageBtn');
let collection = document.getElementById('message-list');
let id: string;

interface Msg {
    message: string;
    id: string;
}

class Chat {
    currentID: string;

    constructor() {
        this.currentID = socket.id;
        this.checkForMessages();
        this.checkForUsers();
    }
    sendMessage(message: string) {
        if(message) {
            socket.emit('chat message', message);
            this.createNewMessage(<Msg>{id, message});
        }
    }
    checkForMessages() {
        socket.on('chat message', (msg: Msg) => {
            this.createNewMessage(<Msg>msg);
        });
    }
    createNewMessage(msg: Msg) {
        let div: HTMLDivElement = <HTMLDivElement>document.createElement('div');
        div.classList.add('card');
        div.classList.add('hoverable');
        let div2: HTMLDivElement = <HTMLDivElement>document.createElement('div');
        div2.classList.add('card-content');
        let spanTitle: HTMLSpanElement = <HTMLSpanElement>document.createElement('span');
        spanTitle.classList.add('card-title');
        spanTitle.innerHTML = `${msg.id} - ${msg.message}`;
        if (id && msg.id === id) {
            div.classList.add('blue-grey');
            div.classList.add('darken-1');
            div2.classList.add('white-text');
        }
        div2.appendChild(spanTitle);
        div.appendChild(div2);
        collection.appendChild(div);
    }
    checkForUsers() {
        socket.on('user joined', (username: string) => {
            this.createNewMessage(<Msg>{id: username, message: `just joined`});
        })
    }
}
socket.on('connect', () => {
    socket.on('username set', (myId: string) => {
        id = myId;
        containerDiv.style.visibility = 'visible';
    });
    let chat = new Chat();
    usernameBtn.onclick = () => {
        let username: string = usernameInput.value;
        if(username) {
            socket.emit('set username', username);
        }
    }
    messageBtn.onclick = () => {
        if(id) {
            let message: string = messageInput.value;
            chat.sendMessage(message);
        }
        
    }
})