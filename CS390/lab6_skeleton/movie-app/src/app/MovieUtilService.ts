import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from './models/movie.interface';

@Injectable({
    providedIn: 'root'
})
export class MovieUtilService {
    private HttpClient: HttpClient;
    constructor(private http: HttpClient) { 
        this.HttpClient = http;
    }

    //Makes an HTTP GET to http://localhost:3000/movies
    getMovies(): Observable<Movie[]> { 
        return this.http.get<Movie[]>('http://localhost:3000/movies');
    }

    //Makes a HTTP GET to http://localhost:3000/movies?q=<query>
    search(query): Observable<Movie[]> { 
        return this.http.get<Movie[]>('http://localhost:3000/movies?q=' + query);
    }
}