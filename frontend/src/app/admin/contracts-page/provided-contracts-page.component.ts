import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ApiClient, Contract} from '../../api-client/api-client.component';
import {ErrorModalService} from '../../app-components/error-modal-service.compoennt';

@Component({
  templateUrl: './provided-contracts-page.component.html'
})
export class ProvidedContractsPageComponent implements OnInit {

  selectedStatus: string;
  isLoading = true;
  contracts: Array<Contract> = [];

  constructor(
    private route: ActivatedRoute,
    private apiClient: ApiClient,
    private errorModalService: ErrorModalService
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.selectedStatus = params['status'];
      this.fetchPage(params);
    });
  }

  fetchPage(params) {
    this.isLoading = true;
    this.apiClient.getProvidedContracts(params).subscribe(
      data => {
        this.contracts = data.items;
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
