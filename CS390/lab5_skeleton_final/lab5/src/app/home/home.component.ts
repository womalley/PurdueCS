import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'cs390wap-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user = {
    name: 'Mary'
  }
  constructor() { }

  ngOnInit() {
  }

}
