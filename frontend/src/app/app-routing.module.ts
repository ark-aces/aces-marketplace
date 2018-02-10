import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ServicesComponent} from "./services-page/services.component";
import {ServicePageComponent} from "./service-page/service-page.component";
import {DashboardPageComponent} from "./dashboard-page/dashboard-page.component";



const routes: Routes = [
  { path: 'services', component: ServicesComponent },
  { path: '', component: DashboardPageComponent },
  { path: 'services/:id', component: ServicePageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
