import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ApiClient, Service, ServiceCategory} from '../../api-client/api-client.component';
import {ErrorModalService} from '../../app-components/error-modal-service.compoennt';
import {Observable} from 'rxjs/Observable';

@Component({
  templateUrl: './admin-service-page.component.html'
})
export class AdminServicePageComponent implements OnInit {

  isLoading = true;
  isActivating = false;
  isDeactivating = false;

  service: Service;
  serviceCategories: Array<ServiceCategory>;

  constructor(
    private route: ActivatedRoute,
    private apiClient: ApiClient,
    private errorModalService: ErrorModalService
  ) {}

  ngOnInit() {
    this.isLoading = true;

    this.route.params.subscribe(params => {
      Observable.forkJoin(
        this.apiClient.getAccountService(params['id']),
        this.apiClient.getServiceCategories()
      ).subscribe(
        data => {
          this.service = data[0];
          this.serviceCategories = data[1];
          this.isLoading = false;
        },
        error => {
          console.log(error);
          this.errorModalService.showDefaultError();
          this.isLoading = false;
        }
      );
    });
  }

  deactivateService() {
    this.isDeactivating = true;
    this.apiClient.updateService(this.service.id, {status: 'inactive'})
      .subscribe(
        data => {
          this.service.status = 'inactive';
          this.isDeactivating = false;
        },
        error => {
          console.log(error);
          this.errorModalService.showError('Failed', 'Failed to update status. Please try again later.');
          this.isDeactivating = false;
        }
      );
  }

  activateService() {
    this.isActivating = true;
    this.apiClient.updateService(this.service.id, {status: 'active'})
      .subscribe(
        data => {
          this.service.status = 'active';
          this.isActivating = false;
        },
        error => {
          console.log(error);
          this.errorModalService.showError('Failed', 'Failed to update status. Please try again later.');
          this.isActivating = false;
        }
      );
  }

}
