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
import {AdminLayoutComponent} from './admin/admin-layout.component';
import {AdminDashboardPageComponent} from './admin/dashboard/admin-dashboard-page.component';
import {ConsumerLayoutComponent} from './consumer-layout.component';
import {AdminServicesPageComponent} from './admin/services-page/admin-services-page.component';
import {AdminCreateServicePageComponent} from './admin/create-service-page/admin-create-service-page.component';
import {AdminServiceLauncherPageComponent} from './admin/service-launcher/admin-service-launcher-page.component';
import {AdminSettingsPageComponent} from './admin/settings-page/admin-settings-page.component';
import {AdminUsersPageComponent} from './admin/users-page/admin-users-page.component';
import {AdminServicePageComponent} from './admin/service-page/admin-service-page.component';
import {ProvidedContractsPageComponent} from './admin/contracts-page/provided-contracts-page.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      { path: 'admin', redirectTo: '/admin/dashboard', pathMatch: 'full' },
      { path: 'admin/dashboard', component: AdminDashboardPageComponent, pathMatch: 'full'},
      { path: 'admin/services', redirectTo: '/admin/services/browse', pathMatch: 'full'},
      { path: 'admin/services/browse', component: AdminServicesPageComponent},
      { path: 'admin/services/register-service', component: AdminCreateServicePageComponent},
      { path: 'admin/services/:id', component: AdminServicePageComponent},
      { path: 'admin/service-launcher', component: AdminServiceLauncherPageComponent},
      { path: 'admin/contracts', component: ProvidedContractsPageComponent},
      { path: 'admin/users', component: AdminUsersPageComponent},
      { path: 'admin/settings', component: AdminSettingsPageComponent}
    ]
  },
  {
    path: '',
    component: ConsumerLayoutComponent,
    children: [
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
      { path: 'contracts', component: ContractsPageComponent},
      { path: 'contracts/:id', component: ContractPageComponent },
      { path: 'settings', component: SettingsPageComponent },
      { path: 'reset-password-request', component: ResetPasswordRequestPageComponent },
      { path: 'reset-password', component: ResetPasswordPageComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
