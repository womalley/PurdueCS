import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { HomeModule } from './home/home.module';
import { SharedModule } from './shared/shared.module';


@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    HomeModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
