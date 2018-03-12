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
    TermsPageComponent,
    CreateAccountFormComponent,
    EmailVerificationPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    AppConfigModule,
    RecaptchaModule.forRoot(),
    RecaptchaFormsModule
  ],
  providers: [
    ApiClient
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
