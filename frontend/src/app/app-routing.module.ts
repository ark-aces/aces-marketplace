import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ServicesComponent} from './services-page/services.component';
import {ServicePageComponent} from './service-page/service-page.component';
import {DashboardPageComponent} from './dashboard-page/dashboard-page.component';
import {CreateAccountPageComponent} from './create-account-page/create-account-page.component';
import {SignInPageComponent} from './sign-in-page/sign-in-page.component';
import {TermsPageComponent} from './terms-page/terms-page.component';
import {EmailVerificationPageComponent} from './email-verification/email-verification-page.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardPageComponent },
  { path: 'services', component: ServicesComponent},
  { path: 'services', redirectTo: '/services/browse', pathMatch: 'full' },
  { path: 'services/browse', component: ServicesComponent},
  { path: 'services/new', component: ServicesComponent},
  { path: 'services/:id', component: ServicePageComponent },
  { path: 'sign-in', component: SignInPageComponent },
  { path: 'create-account', component: CreateAccountPageComponent },
  { path: 'terms', component: TermsPageComponent },
  { path: 'email-verification', component: EmailVerificationPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
