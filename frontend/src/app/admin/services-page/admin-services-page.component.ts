import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ApiClient, Service} from '../../api-client/api-client.component';
import {ErrorModalService} from '../../app-components/error-modal-service.compoennt';

@Component({
  templateUrl: './admin-services-page.component.html'
})
export class AdminServicesPageComponent implements OnInit {

  isLoading = true;
  services: Array<Service> = [];

  constructor(
    private route: ActivatedRoute,
    private apiClient: ApiClient,
    private errorModalService: ErrorModalService
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.fetchPage(params);
    });
  }

  fetchPage(params) {
    this.isLoading = true;
    this.apiClient.getAccountServices(params).subscribe(
      data => {
        this.services = data.items;
        this.isLoading = false;
      },
      error => {
        console.log(error);
        this.errorModalService.showDefaultError();
        this.isLoading = false;
      }
    );
  }

}
