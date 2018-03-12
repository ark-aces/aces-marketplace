import {Inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {APP_CONFIG, AppConfig} from '../app-config-module';

export interface Service {
  id: string;
  url: string;
  name: string;
  version: string;
  description: string;
  websiteUrl: string;
  createdAt: string;
}

export interface ServicesResponse {
  pageSize: number;
  page: number;
  totalItems: number;
  items: Array<Service>;
}

export interface CreateAccountRequest {
  contactEmailAddress: string;
  userEmailAddress: string;
  userName: string;
  userPassword: string;
  arkWalletAddress: string;
  agreeToTerms: boolean;
  recaptchaCode: string;
}

export interface EmailVerificationRequest {
  code: string;
}

export interface FieldError {
  field: string;
  code: string;
  message: string;
}

export interface ValidationError {
  code: string;
  message: string;
  fieldErrors: Array<FieldError>;
}

@Injectable()
export class ApiClient {

  constructor(
    private http: HttpClient,
    @Inject(APP_CONFIG) private config: AppConfig
  ) {
  }

  getServices() {
    return this.http.get<ServicesResponse>(this.config.apiEndpoint + '/services');
  }

  getService(id: string) {
    return this.http.get<Service>(this.config.apiEndpoint + '/services/' + id);
  }

  postAccount(createAccountRequest: CreateAccountRequest) {
    return this.http.post(this.config.apiEndpoint + '/registrations', createAccountRequest);
  }

  postEmailVerification(emailVerificationRequest: EmailVerificationRequest) {
    return this.http.post(this.config.apiEndpoint + '/emailVerifications', emailVerificationRequest);
  }

}
