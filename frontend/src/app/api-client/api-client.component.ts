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

}
