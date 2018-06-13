import {Component, Inject, OnInit} from '@angular/core';
import {ApiClient} from '../../api-client/api-client.component';
import {APP_CONFIG, AppConfig} from '../../app-config-module';

class ServiceCategory {
  public id: number;
  public name: string;
}

@Component({
  templateUrl: './admin-create-service-page.component.html',
})
export class AdminCreateServicePageComponent implements OnInit {

  isLoading = true;
  serviceCategories: Array<ServiceCategory>;

  constructor(
    private apiClient: ApiClient,
    @Inject(APP_CONFIG) private config: AppConfig
  ) {
  }

  ngOnInit() {
    this.isLoading = true;
    this.apiClient
      .getServiceCategories()
      .subscribe(
        result => {
          console.log(result);
          this.serviceCategories = result;
          this.isLoading = false;
        },
        error => {
          this.isLoading = false;
        }
      );
  }

}
