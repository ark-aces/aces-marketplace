import {Component, Inject} from '@angular/core';
import {Router} from '@angular/router';
import {ApiClient, FieldError, ValidationError} from '../api-client/api-client.component';
import {APP_CONFIG, AppConfig} from '../app-config-module';
import {HttpErrorResponse} from '@angular/common/http';
import {ErrorModalService} from '../app-components/error-modal-service.compoennt';

class CreateAccountModel {
  emailAddress: string;
  name: string;
  password: string;
  arkWalletAddress: string;
  agreeToTerms: boolean;
  recaptchaCode: string;
}

@Component({
  selector: 'app-create-account-form',
  templateUrl: './create-account-form.component.html',
})
export class CreateAccountFormComponent {

  recaptchaSiteKey: string;

  isSubmitted = false;
  canSubmit = true;
  isSuccess = false;
  hasErrors = false;

  model = new CreateAccountModel();

  emailAddressHasError = false;
  emailAddressFieldErrors: Array<FieldError> = [];

  nameHasError = false;
  nameFieldErrors: Array<FieldError> = [];

  passwordHasError = false;
  passwordFieldErrors: Array<FieldError> = [];

  arkWalletAddressHasError = false;
  arkWalletAddressFieldErrors: Array<FieldError> = [];

  recaptchaCodeHasError = false;
  recaptchaCodeFieldErrors: Array<FieldError> = [];

  agreeToTermsHasError = false;
  agreeToTermsFieldErrors: Array<FieldError> = [];

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

    this.nameHasError = false;
    this.nameFieldErrors = [];

    this.passwordHasError = false;
    this.passwordFieldErrors = [];

    this.arkWalletAddressHasError = false;
    this.arkWalletAddressFieldErrors = [];

    this.recaptchaCodeHasError = false;
    this.recaptchaCodeFieldErrors = [];

    this.agreeToTermsHasError = false;
    this.agreeToTermsFieldErrors = [];

    this.isSubmitted = true;

    this.apiClient
      .postAccount({
        contactEmailAddress: this.model.emailAddress,
        userEmailAddress: this.model.emailAddress,
        userName: this.model.name,
        userPassword: this.model.password,
        arkWalletAddress: this.model.arkWalletAddress,
        agreeToTerms: this.model.agreeToTerms,
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
              if (fieldError.field === 'userEmailAddress') {
                this.emailAddressHasError = true;
                this.emailAddressFieldErrors.push(fieldError);
              }
              if (fieldError.field === 'userName') {
                this.nameHasError = true;
                this.nameFieldErrors.push(fieldError);
              }
              if (fieldError.field === 'userPassword') {
                this.passwordHasError = true;
                this.passwordFieldErrors.push(fieldError);
              }
              if (fieldError.field === 'arkWalletAddress') {
                this.arkWalletAddressHasError = true;
                this.arkWalletAddressFieldErrors.push(fieldError);
              }
              if (fieldError.field === 'recaptchaCode') {
                this.recaptchaCodeHasError = true;
                this.recaptchaCodeFieldErrors.push(fieldError);
              }
              if (fieldError.field === 'agreeToTerms') {
                this.agreeToTermsHasError = true;
                this.agreeToTermsFieldErrors.push(fieldError);
              }
            }
            this.model.recaptchaCode = '';
          } else {
            console.log(response);
            this.errorModalService.showDefaultError();
          }

          this.isSubmitted = false;
          window.scrollTo(0, 0);
        }
      );
  }

}
