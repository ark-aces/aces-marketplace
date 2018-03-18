import {Component, Input} from '@angular/core';
import {Router} from '@angular/router';
import {ApiClient, FieldError, ValidationError} from '../api-client/api-client.component';
import {HttpErrorResponse} from '@angular/common/http';

class ResetPasswordForm {
  password: string;
}

@Component({
  selector: 'app-reset-password-form',
  templateUrl: './reset-password-form.component.html',
})
export class ResetPasswordFormComponent {

  @Input()
  code: string;

  recaptchaSiteKey: string;

  isSubmitted = false;
  canSubmit = true;
  isSuccess = false;
  hasErrors = false;

  model = new ResetPasswordForm();

  passwordHasError = false;
  passwordFieldErrors: Array<FieldError> = [];

  codeHasError = false;
  codeFieldErrors: Array<FieldError> = [];

  constructor(
    private router: Router,
    private apiClient: ApiClient
  ) {
  }

  onSubmit() {
    this.passwordHasError = false;
    this.passwordFieldErrors = [];

    this.codeHasError = false;
    this.codeFieldErrors = [];

    this.isSubmitted = true;

    this.apiClient
      .createPasswordReset({
        code: this.code,
        password: this.model.password
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
              if (fieldError.field === 'password') {
                this.passwordHasError = true;
                this.passwordFieldErrors.push(fieldError);
              }
              if (fieldError.field === 'code') {
                this.codeHasError = true;
                this.codeFieldErrors.push(fieldError);
              }
            }
          } else {
            // todo: alert unexpected error
          }

          this.isSubmitted = false;
        }
      );
  }

}
