import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {APP_CONFIG, AppConfig} from '../app-config-module';
import {AuthService} from '../auth/auth-service.component';

export interface Capacity {
  unit: string;
  value: number;
}

export interface Service {
  id: string;
  url: string;
  name: string;
  version: string;
  description: string;
  websiteUrl: string;
  isTestnet: boolean;
  createdAt: string;
  capacities: Array<Capacity>;
  flatFee: number;
  percentFee: number;
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

export interface OauthToken {
  access_token: string;
  token_type: string;
  refresh_token: string;
  expires_in: number;
  scope: string;
}

export interface User {
  id: string;
  emailAddress: string;
  name: string;
  createdAt: string;
}

export interface ResetPasswordRequestRequest {
  emailAddress: string;
  recaptchaCode: string;
}

export interface PasswordResetRequest {
  code: string;
  password: string;
}

export interface CreateContractRequest {
  arguments: object;
}

export interface Contract {
  id: string;
  status: string;
  createdAt: string;
  results: object;
  serviceId: string;
}

export interface ContractsResponse {
  pageSize: number;
  page: number;
  totalItems: number;
  items: Array<Contract>;
}

@Injectable()
export class ApiClient {

  constructor(
    private http: HttpClient,
    @Inject(APP_CONFIG) private config: AppConfig,
    private authService: AuthService
  ) {}

  createOauthToken(username: string, password: string) {
    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);
    params.append('grant_type', 'password');
    params.append('client_id', 'marketplace');

    return this.http.post<OauthToken>(this.config.apiEndpoint + '/oauth/token', params.toString(), {
      headers: new HttpHeaders({
        'Content-type': 'application/x-www-form-urlencoded',
        'Authorization': 'Basic ' + btoa('marketplace:secret')
      })
    });
  }

  getServices() {
    return this.http.get<ServicesResponse>(this.config.apiEndpoint + '/services');
  }

  getService(id: string) {
    return this.http.get<Service>(this.config.apiEndpoint + '/services/' + id);
  }

  getServiceInfo(id: string) {
    return this.http.get(this.config.apiEndpoint + '/services/' + id + '/info');
  }

  createContract(serviceId: string, createContractRequest: CreateContractRequest) {
    return this.http.post<Contract>(this.config.apiEndpoint + '/services/' + serviceId + '/contracts', createContractRequest,
      {
        headers: new HttpHeaders({
          'Authorization': 'Bearer ' + this.authService.accessToken
        })
      });
  }

  getContract(contractId: string) {
    return this.http.get<Contract>(this.config.apiEndpoint + '/account/contracts/' + contractId,
      {
        headers: new HttpHeaders({
          'Authorization': 'Bearer ' + this.authService.accessToken
        })
      });
  }

  getContracts(queryParams) {
    return this.http.get<ContractsResponse>(this.config.apiEndpoint + '/account/contracts',
      {
        params: queryParams,
        headers: new HttpHeaders({
          'Authorization': 'Bearer ' + this.authService.accessToken
        })
      });
  }

  postAccount(createAccountRequest: CreateAccountRequest) {
    return this.http.post(this.config.apiEndpoint + '/registrations', createAccountRequest);
  }

  postEmailVerification(emailVerificationRequest: EmailVerificationRequest) {
    return this.http.post(this.config.apiEndpoint + '/emailVerifications', emailVerificationRequest);
  }

  createResetPasswordRequest(resetPasswordRequest: ResetPasswordRequestRequest) {
    return this.http.post(this.config.apiEndpoint + '/resetPasswordRequests', resetPasswordRequest);
  }

  createPasswordReset(passwordResetRequest: PasswordResetRequest) {
    return this.http.post(this.config.apiEndpoint + '/passwordResets', passwordResetRequest);
  }

  getCurrentUser() {
    return this.http.get<User>(this.config.apiEndpoint + '/user', {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + this.authService.accessToken
      })
    });
  }

}
