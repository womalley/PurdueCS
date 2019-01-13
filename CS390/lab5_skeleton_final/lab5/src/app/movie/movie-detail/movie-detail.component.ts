import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Movie } from '../../shared/models/movie.interface';
import { MovieUtilService } from '../../shared/movie-util.service';

@Component({
  selector: 'cs390wap-movie-detail',
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.css']
})
export class MovieDetailComponent implements OnInit {
  movie: Movie;
  mov1: MovieUtilService;
  id: number;
  constructor(private route: ActivatedRoute, private mov: MovieUtilService) {
    const movieId = +this.route.snapshot.params['id'];
    this.mov1 = mov;
    this.id = movieId;
  }

  ngOnInit() {
    this.movie = this.mov1.getMovie(this.id);
  }

}
