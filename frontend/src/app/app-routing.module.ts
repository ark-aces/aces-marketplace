import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ServicesComponent} from './services-page/services.component';
import {ServicePageComponent} from './service-page/service-page.component';
import {DashboardPageComponent} from './dashboard-page/dashboard-page.component';
import {CreateAccountPageComponent} from './create-account-page/create-account-page.component';
import {SignInPageComponent} from './sign-in-page/sign-in-page.component';
import {TermsPageComponent} from './terms-page/terms-page.component';
import {EmailVerificationPageComponent} from './email-verification/email-verification-page.component';
import {SignOutPageComponent} from './sign-out-page/sign-out-page.component';
import {SignOutSuccessPageComponent} from './sign-out-success-page/sign-out-success-page.component';
import {ContractsPageComponent} from './contracts-page.component.ts/contracts-page.component';
import {SettingsPageComponent} from './settings-page/settings-page.component';
import {ResetPasswordRequestPageComponent} from './reset-password-page/reset-password-request-page.component';
import {ResetPasswordPageComponent} from './reset-password-page/reset-password-page.component';
import {ContractPageComponent} from './contract-page.component.ts/contract-page.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardPageComponent },
  { path: 'services', component: ServicesComponent},
  { path: 'services', redirectTo: '/services/browse', pathMatch: 'full' },
  { path: 'services/browse', component: ServicesComponent},
  { path: 'services/new', component: ServicesComponent},
  { path: 'services/:id', component: ServicePageComponent },
  { path: 'sign-in', component: SignInPageComponent },
  { path: 'sign-out', component: SignOutPageComponent },
  { path: 'goodbye', component: SignOutSuccessPageComponent },
  { path: 'create-account', component: CreateAccountPageComponent },
  { path: 'terms', component: TermsPageComponent },
  { path: 'email-verification', component: EmailVerificationPageComponent },
  { path: 'contracts/browse', component: ContractsPageComponent },
  { path: 'contracts', component: ContractsPageComponent },
  { path: 'contracts/:id', component: ContractPageComponent },
  { path: 'settings', component: SettingsPageComponent },
  { path: 'reset-password-request', component: ResetPasswordRequestPageComponent },
  { path: 'reset-password', component: ResetPasswordPageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
