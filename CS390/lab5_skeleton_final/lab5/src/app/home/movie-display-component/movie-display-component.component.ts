import { Component, OnInit, Input } from '@angular/core';
import { Movie } from '../../shared/models/movie.interface';

@Component({
  selector: 'cs390wap-movie-display-component',
  templateUrl: './movie-display-component.component.html',
  styleUrls: ['./movie-display-component.component.css']
})
export class MovieDisplayComponentComponent implements OnInit {

  @Input() movie: Movie;
  constructor() { }

  ngOnInit() {
  }

}
