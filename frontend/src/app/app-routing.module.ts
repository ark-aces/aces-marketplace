import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ServicesComponent} from './services-page/services.component';
import {ServicePageComponent} from './service-page/service-page.component';
import {DashboardPageComponent} from './dashboard-page/dashboard-page.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardPageComponent },
  { path: 'services', component: ServicesComponent},
  { path: 'services', redirectTo: '/services/browse', pathMatch: 'full' },
  { path: 'services/browse', component: ServicesComponent},
  { path: 'services/new', component: ServicesComponent},
  { path: 'services/:id', component: ServicePageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
