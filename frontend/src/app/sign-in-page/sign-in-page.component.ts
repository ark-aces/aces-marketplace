import {Component, Inject, OnInit} from '@angular/core';
import {ApiClient} from '../api-client/api-client.component';
import {HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {APP_CONFIG, AppConfig} from '../app-config-module';
import {AuthService} from '../auth/auth-service.component';
import {ErrorModalService} from '../app-components/error-modal-service.compoennt';

class SignInForm {
  emailAddress: string;
  password: string;
}

@Component({
  templateUrl: './sign-in-page.component.html'
})
export class SignInPageComponent implements OnInit {

  model = new SignInForm();

  isSubmitted = false;
  isSuccess = false;
  hasErrors = false;

  constructor(
    private apiClient: ApiClient,
    private authService: AuthService,
    private router: Router,
    @Inject(APP_CONFIG) private config: AppConfig,
    private errorModalService: ErrorModalService
  ) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated) {
      this.router.navigate(['/dashboard']);
    }
  }

  onSubmit() {
    this.isSubmitted = true;

    this.apiClient
      .createOauthToken(this.model.emailAddress, this.model.password)
      .subscribe(
        result => {
          // get user info and store in cookie
          this.authService.accessToken = result.access_token;
          this.authService.isAuthenticated = true;
          this.apiClient.getCurrentUser()
            .subscribe(
              user => {
                this.authService.user = user;

                const d = new Date();
                d.setDate(d.getDate() + 1);
                const expireDateDays = d;
                this.authService.persistCookie(expireDateDays);

                this.router.navigate(['/dashboard']);
              },
              userError => {
                console.log(userError);
                this.errorModalService.showError(
                  'Account not found',
                  'The email address used was not found. Please create an account or try again.'
                );
                this.isSuccess = false;
                this.isSubmitted = false;
              }
            );

        },
        (response: HttpErrorResponse)  => {
          if (response.status === 401 || response.status === 400) {
            this.hasErrors = true;
          } else {
            console.log(response);
            this.errorModalService.showError(
              'Unexpected Error',
              'An unexpected error occurred getting current user information. Please try again later.'
            );
          }
          this.isSuccess = false;
          this.isSubmitted = false;
        }
      );
  }
}
