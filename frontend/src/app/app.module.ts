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
import {CreateAccountPageComponent} from './create-account-page/create-account-page.component';
import {SignInPageComponent} from './sign-in-page/sign-in-page.component';
import {TermsPageComponent} from './terms-page/terms-page.component';
import {CreateAccountFormComponent} from './create-account-page/create-account-form.component';
import {RecaptchaModule} from 'ng-recaptcha';
import {EmailVerificationPageComponent} from './email-verification/email-verification-page.component';
import {RecaptchaFormsModule} from 'ng-recaptcha/forms';
import {SignOutPageComponent} from './sign-out-page/sign-out-page.component';
import {SignOutSuccessPageComponent} from './sign-out-success-page/sign-out-success-page.component';
import {Ng2Webstorage} from 'ngx-webstorage';
import { CookieService } from 'ngx-cookie-service';
import {AuthService} from './auth/auth-service.component';
import {ContractsPageComponent} from './contracts-page.component.ts/contracts-page.component';
import {SettingsPageComponent} from './settings-page/settings-page.component';
import {ResetPasswordPageComponent} from './reset-password-page/reset-password-page.component';
import {ResetPasswordRequestPageComponent} from './reset-password-page/reset-password-request-page.component';
import {ResetPasswordRequestFormComponent} from './reset-password-page/reset-password-request-form.component';
import {ResetPasswordFormComponent} from './reset-password-page/reset-password-form.component';

@NgModule({
  declarations: [
    AppComponent,
    TopNavComponent,
    SidebarMenuComponent,
    DashboardPageComponent,
    ServicePageComponent,
    ServicesComponent,
    ServiceFormComponent,
    CreateAccountPageComponent,
    SignInPageComponent,
    SignOutPageComponent,
    SignOutSuccessPageComponent,
    TermsPageComponent,
    CreateAccountFormComponent,
    EmailVerificationPageComponent,
    ContractsPageComponent,
    SettingsPageComponent,
    ResetPasswordPageComponent,
    ResetPasswordRequestPageComponent,
    ResetPasswordFormComponent,
    ResetPasswordRequestFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    AppConfigModule,
    RecaptchaModule.forRoot(),
    RecaptchaFormsModule,
    Ng2Webstorage
  ],
  providers: [
    ApiClient,
    AuthService,
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
