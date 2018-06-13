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
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ApiClient} from './api-client/api-client.component';
import {AppConfigModule} from './app-config-module';
import {ServiceFormComponent} from './service-page/service-form.component';
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
import {FormOptionalComponent} from './form-components/form-optional-component';
import {ContractPageComponent} from './contract-page.component.ts/contract-page.component';
import {ModalModule} from 'ngx-bootstrap';
import {ErrorModalComponent} from './app-components/error-modal.component';
import {ErrorModalService} from './app-components/error-modal-service.compoennt';
import { MarkdownModule } from 'ngx-markdown';
import {TokenInterceptor} from './auth/token-interceptor';
import {ClipboardModule} from 'ngx-clipboard';
import {ServiceCapacityBoxComponent} from './app-components/service-capacity-box.component';
import {ServiceFeesBoxComponent} from './app-components/service-fees-box.component';
import {ServicePercentFeesBoxComponent} from './app-components/service-percent-fees-box.component';
import { NgxSpinnerModule } from 'ngx-spinner';
import 'rxjs/Rx';
import {AdminDashboardPageComponent} from './admin/dashboard/admin-dashboard-page.component';
import {AdminLayoutComponent} from './admin/admin-layout.component';
import {ConsumerLayoutComponent} from './consumer-layout.component';
import {AdminTopNavComponent} from './admin/admin-top-nav.component';
import {AdminSidebarMenuComponent} from './admin/admin-sidebar-menu.component';
import {AdminServicesPageComponent} from './admin/services-page/admin-services-page.component';
import {AdminServiceLauncherPageComponent} from './admin/service-launcher/admin-service-launcher-page.component';
import {AdminCreateServicePageComponent} from './admin/create-service-page/admin-create-service-page.component';
import {AdminCreateServiceFormComponent} from './admin/create-service-page/admin-create-service-form.component';
import {AdminSettingsPageComponent} from './admin/settings-page/admin-settings-page.component';
import {AdminUsersPageComponent} from './admin/users-page/admin-users-page.component';
import {AdminServicePageComponent} from './admin/service-page/admin-service-page.component';
import {ProvidedContractsPageComponent} from './admin/contracts-page/provided-contracts-page.component';
import {AdminUpdateServiceFormComponent} from './admin/service-page/admin-update-service-form.component';

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
    ContractPageComponent,
    SettingsPageComponent,
    ResetPasswordPageComponent,
    ResetPasswordRequestPageComponent,
    ResetPasswordFormComponent,
    ResetPasswordRequestFormComponent,
    ServiceFormComponent,
    ServiceCapacityBoxComponent,
    ServiceFeesBoxComponent,
    ServicePercentFeesBoxComponent,
    FormOptionalComponent,
    ErrorModalComponent,
    ConsumerLayoutComponent,
    AdminLayoutComponent,
    AdminDashboardPageComponent,
    AdminTopNavComponent,
    AdminSidebarMenuComponent,
    AdminServicesPageComponent,
    AdminServiceLauncherPageComponent,
    AdminCreateServicePageComponent,
    AdminCreateServiceFormComponent,
    AdminSettingsPageComponent,
    AdminUsersPageComponent,
    AdminServicePageComponent,
    ProvidedContractsPageComponent,
    AdminUpdateServiceFormComponent
  ],
  imports: [
    ClipboardModule,
    MarkdownModule.forRoot(),
    ModalModule.forRoot(),
    RecaptchaModule.forRoot(),
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    AppConfigModule,
    RecaptchaFormsModule,
    Ng2Webstorage,
    NgxSpinnerModule
  ],
  providers: [
    ApiClient,
    AuthService,
    CookieService,
    ErrorModalService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    }
  ],
  entryComponents: [ErrorModalComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
