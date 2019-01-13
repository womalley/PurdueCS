import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { MovieDisplayComponent } from './movie-display/movie-display.component';
import { MovieListComponent } from './movie-list/movie-list.component';
import { HttpClientModule } from '@angular/common/http';
import { MovieUtilService } from './MovieUtilService';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MovieDisplayComponent,
    MovieListComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [MovieUtilService],
  bootstrap: [AppComponent]
})
export class AppModule { }
