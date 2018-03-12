import {Component, Inject} from '@angular/core';
import {ApiClient, Service} from '../api-client/api-client.component';
import {APP_CONFIG, AppConfig} from '../app-config-module';

@Component({
  templateUrl: './sign-in-page.component.html'
})
export class SignInPageComponent {

  constructor(
    private apiClient: ApiClient,
    @Inject(APP_CONFIG) private config: AppConfig
  ) {
  }

}
