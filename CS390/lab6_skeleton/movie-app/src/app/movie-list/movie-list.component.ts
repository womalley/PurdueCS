import { Component, OnInit } from '@angular/core';
import { Movie } from '../models/movie.interface';
import { FormControl } from '@angular/forms';
import { MovieUtilService } from '../MovieUtilService';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {
  movies: Movie[];
  searchControl: FormControl;
  private MovieUtilService: MovieUtilService;
  search: "";

  constructor(private MovieUtilServ: MovieUtilService) {
    this.searchControl = new FormControl();
    this.MovieUtilService = MovieUtilServ;
    this.movies = new Array<Movie>();
    this.search = "";

    /* set up movies data and search here */
    /* Note: this.searchControl.valueChanges returns a Observable<string> */
  }
  
  ngOnInit() {
    this.MovieUtilServ.getMovies().subscribe(res => {
      this.movies = res;
    },
    error => {
      console.error("ERROR: Something went wrong");
      this.movies = []; //empty array
    });

    this.searchControl.valueChanges.pipe(
        debounceTime(400)
      )
      .subscribe(res => {
        if (res !== "" && res !== this.search) {
          this.MovieUtilServ.search(res).subscribe(re => {
            this.movies = re;
            this.search = res; //last query string
          },
          error => {
            console.error(error);
            this.movies = []; //empty array
          });
        }
        //re-populate list
        else if (res !== this.search) {
          this.MovieUtilServ.search(res).subscribe(re => {
            this.movies = re;
            this.search = "";
          },
          error => {
            console.error(error);
            this.movies = []; //empty array
          });
        }
      });
  }
}
