import { NgModule, InjectionToken } from '@angular/core';
import { environment } from '../environments/environment';

export let APP_CONFIG = new InjectionToken<AppConfig>('app.config');

export class AppConfig {
  cookieDomain: string;
  useSecureCookie: boolean;
  apiEndpoint: string;
  recaptchaSiteKey: string;
}

export const APP_DI_CONFIG: AppConfig = {
  cookieDomain: environment.cookieDomain,
  useSecureCookie: environment.useSecureCookie,
  apiEndpoint: environment.apiEndpoint,
  recaptchaSiteKey: environment.recaptchaSiteKey
};

@NgModule({
  providers: [{
    provide: APP_CONFIG,
    useValue: APP_DI_CONFIG
  }]
})
export class AppConfigModule { }
