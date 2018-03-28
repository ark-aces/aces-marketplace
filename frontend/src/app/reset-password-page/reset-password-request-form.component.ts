import {Component, Inject} from '@angular/core';
import {Router} from '@angular/router';
import {ApiClient, FieldError, ValidationError} from '../api-client/api-client.component';
import {APP_CONFIG, AppConfig} from '../app-config-module';
import {HttpErrorResponse} from '@angular/common/http';
import {ErrorModalService} from '../app-components/error-modal-service.compoennt';

class ResetPasswordForm {
  emailAddress: string;
  recaptchaCode: string;
}

@Component({
  selector: 'app-reset-password-request-form',
  templateUrl: './reset-password-request-form.component.html',
})
export class ResetPasswordRequestFormComponent {

  recaptchaSiteKey: string;

  isSubmitted = false;
  canSubmit = true;
  isSuccess = false;
  hasErrors = false;

  model = new ResetPasswordForm();

  emailAddressHasError = false;
  emailAddressFieldErrors: Array<FieldError> = [];

  recaptchaCodeHasError = false;
  recaptchaCodeFieldErrors: Array<FieldError> = [];

  constructor(
    private router: Router,
    private apiClient: ApiClient,
    private errorModalService: ErrorModalService,
    @Inject(APP_CONFIG) private config: AppConfig
  ) {
    this.recaptchaSiteKey = config.recaptchaSiteKey;
  }

  onSubmit() {
    this.emailAddressHasError = false;
    this.emailAddressFieldErrors = [];

    this.recaptchaCodeHasError = false;
    this.recaptchaCodeFieldErrors = [];

    this.isSubmitted = true;

    this.apiClient
      .createResetPasswordRequest({
        emailAddress: this.model.emailAddress,
        recaptchaCode: this.model.recaptchaCode,
      })
      .subscribe(
        result => {
          this.isSuccess = true;
        },
        (response: HttpErrorResponse)  => {
          if (response.status === 400) {
            this.hasErrors = true;
            const validationError = <ValidationError> response.error;
            for (const fieldError of validationError.fieldErrors) {
              if (fieldError.field === 'emailAddress') {
                this.emailAddressHasError = true;
                this.emailAddressFieldErrors.push(fieldError);
              }
              if (fieldError.field === 'recaptchaCode') {
                this.recaptchaCodeHasError = true;
                this.recaptchaCodeFieldErrors.push(fieldError);
              }
            }
            this.model.recaptchaCode = '';
          } else {
            console.log(response);
            this.errorModalService.showDefaultError();
          }

          this.isSubmitted = false;
        }
      );
  }

}
