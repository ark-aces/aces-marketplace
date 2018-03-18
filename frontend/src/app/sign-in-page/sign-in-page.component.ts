import {Component, Inject, OnInit} from '@angular/core';
import {ApiClient} from '../api-client/api-client.component';
import {HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {APP_CONFIG, AppConfig} from '../app-config-module';
import {AuthService} from '../auth/auth-service.component';

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
    @Inject(APP_CONFIG) private config: AppConfig
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
                this.authService.persistCookie();

                // todo: set expire data for refresh
                const expireDate = new Date().getTime() + (1000 * result.expires_in);
                this.router.navigate(['/dashboard']);
              },
              userError => {
                // todo: modal alert unexpected error
                console.log('An unexpected error occurred getting current user information');
                console.log(userError);
                this.isSuccess = false;
                this.isSubmitted = false;
              }
            );

        },
        (response: HttpErrorResponse)  => {
          console.log('error');
          console.log(response);
          if (response.status === 401 || response.status === 400) {
            this.hasErrors = true;
          } else {
            // todo: modal alert unexpected error
            console.log('An unexpected error occurred');
            console.log(response);
          }
          this.isSuccess = false;
          this.isSubmitted = false;
        }
      );
  }
}
