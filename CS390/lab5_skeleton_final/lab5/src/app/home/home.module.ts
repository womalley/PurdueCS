import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MovieListComponent } from './movie-list/movie-list.component';
import { MovieDisplayComponentComponent } from './movie-display-component/movie-display-component.component';
import { HomeComponent } from './home.component';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    RouterModule
  ],
  declarations: [
    HomeComponent,
    MovieListComponent,
    MovieDisplayComponentComponent,
  ]
})
export class HomeModule { }
