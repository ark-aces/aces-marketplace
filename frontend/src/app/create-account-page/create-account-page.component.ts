import {Component, Inject} from '@angular/core';
import {ApiClient} from '../api-client/api-client.component';
import {APP_CONFIG, AppConfig} from '../app-config-module';

@Component({
  templateUrl: './create-account-page.component.html',
})
export class CreateAccountPageComponent {

  loading = true;
  recaptchaSiteKey: string;

  constructor(
    private apiClient: ApiClient,
    @Inject(APP_CONFIG) private config: AppConfig
  ) {
    this.recaptchaSiteKey = config.recaptchaSiteKey;
  }

}
