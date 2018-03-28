import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ApiClient, Contract} from '../api-client/api-client.component';

@Component({
  templateUrl: './contracts-page.component.html'
})
export class ContractsPageComponent implements OnInit {

  isLoading = true;
  contracts: Array<Contract> = [];

  constructor(private route: ActivatedRoute, private apiClient: ApiClient) {}

  ngOnInit() {
    this.apiClient.getContracts().subscribe(
      data => {
        this.contracts = data.items;
      },
      error => {
        // todo: alert error
        console.log(error);
      }
    );
  }

}
