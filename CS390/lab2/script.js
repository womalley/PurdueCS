class NoteComponent {
    constructor() {

    }

    /* 
        takes in a noteList of type Note, query of type string
        and returns a list that has been filtered using the title property of each note
        and the query parameter.
    */
    filterList(noteList, query) {
        return noteList.filter(note => note.name.indexOf(query) !== -1);
    }

}

let noteID = 0;

function newNote() {
    let note = {
        name: document.getElementById("addTitle").value,
        description: document.getElementById("addDescription").value
    }

    if (note.name === "" || note.description === "") {
        //TODO: add user input error message
    }
    else {
        
        //add to array noteList
        let NoteCard = new Note(note.name, note.description, noteID);
        noteList.push(NoteCard);
        console.log(noteList);

        //display note
        let a = "<li id='" + noteID + "'><div class=list-group><a href=# class=list-group-item list-group-item-action flex-column align-items-start id='noteList'>";        
        let b = "<div class=d-flex w-100 justify-content-between><h5 class=noteTitle id=noteTitle>";
        let c = "</h5></div><p class=noteDesc id=noteDesc>";
        let d = "</p></a></div></li>";
        let delBtn = "<span class='delBtn' id='delBtn' onclick='delBtn(" + noteID + ")'>X</span>";

        noteID++;

        let final = a + delBtn + b + note.name + c + note.description + d;
        document.getElementsByClassName("list-group")[0].innerHTML += final;

        //reset inputs
        document.getElementById("addTitle").value = "";
        document.getElementById("addDescription").value = "";
    }
}

function delBtn(noteID) {

    //delete from array
    let index = 0;
    while(index < noteList.length) {
        if (noteID === noteList[index].noteID) {
            noteList.splice(index, 1);
            break;
        }
        index++;
    }
    
    //delete from display
    console.log("close: " + noteID);
    document.getElementById(noteID).outerHTML = "";
}

let noteList = new Array();

class Note {
    constructor(name, description, noteID) {
        this.name = name;
        this.description = description;
        this.noteID = noteID;
    }
}

document.getElementById("searchInput").addEventListener("keyup", function(search) {

    let text = document.getElementById("searchInput").value;
    let noteComp = new NoteComponent();

    document.getElementsByClassName("list-group")[0].innerHTML = "";
    let searchList = null;
    if (text === "") {
        searchList = noteList;
    }
    else {        
        //display filtered list
        searchList = noteComp.filterList(noteList, text);
    }

    //console.log(searchList);

    let i = 0;
    while(i < searchList.length) {
        
        //re render list
        let name = searchList[i].name;
        let description = searchList[i].description;
        let noteID = searchList[i].noteID;
        
        //display note
        let a = "<li id='" + noteID + "'><div class=list-group><a href=# class=list-group-item list-group-item-action flex-column align-items-start id='noteList'>";        
        let b = "<div class=d-flex w-100 justify-content-between><h5 class=noteTitle id=noteTitle>";
        let c = "</h5></div><p class=noteDesc id=noteDesc>";
        let d = "</p></a></div></li>";
        let delBtn = "<span class='delBtn' id='delBtn' onclick='delBtn(" + noteID + ")'>X</span>";

        let final = a + delBtn + b + name + c + description + d;
        document.getElementsByClassName("list-group")[0].innerHTML += final;
        i++;
    }
});