import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { ServicesComponent } from './services-page/services.component';

import { FormsModule} from "@angular/forms";
import {TopNavComponent} from "./top-nav.component";
import {SidebarMenuComponent} from "./sidebar-menu.component";
import {ServicePageComponent} from "./service-page/service-page.component";
import {DashboardPageComponent} from "./dashboard-page/dashboard-page.component";



@NgModule({
  declarations: [
    AppComponent,
    TopNavComponent,
    DashboardPageComponent,
    SidebarMenuComponent,
    ServicePageComponent,
    ServicesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
