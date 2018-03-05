import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { ServicesComponent } from './services-page/services.component';

import { FormsModule} from '@angular/forms';
import {TopNavComponent} from './top-nav.component';
import {SidebarMenuComponent} from './sidebar-menu.component';
import {ServicePageComponent} from './service-page/service-page.component';
import {DashboardPageComponent} from './dashboard-page/dashboard-page.component';
import {HttpClientModule} from '@angular/common/http';
import {ApiClient} from './api-client/api-client.component';
import {AppConfigModule} from './app-config-module';
import {ServiceFormComponent} from './service-form/service-form.component';

@NgModule({
  declarations: [
    AppComponent,
    TopNavComponent,
    DashboardPageComponent,
    SidebarMenuComponent,
    ServicePageComponent,
    ServicesComponent,
    ServiceFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    AppConfigModule
  ],
  providers: [
    ApiClient
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
