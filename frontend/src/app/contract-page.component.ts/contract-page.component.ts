import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ApiClient, Contract} from '../api-client/api-client.component';
import {ErrorModalService} from '../app-components/error-modal-service.compoennt';
import {Subject} from 'rxjs/Subject';

class ResultRow {
  name: string;
  value: object;
}

class ResultArray {
  name: string;
  rows: Array<ResultRow>;
}

@Component({
  templateUrl: './contract-page.component.html'
})
export class ContractPageComponent implements OnInit, OnDestroy {

  id: string;
  contract: Contract;
  resultRows: Array<ResultRow> = [];
  resultArrays: Array<ResultArray> = [];
  isLoading = true;

  interval: any;

  constructor(
    private route: ActivatedRoute,
    private apiClient: ApiClient,
    private errorModalService: ErrorModalService
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params['id'];

      this.checkForUpdates();
      this.interval = setInterval(() => {
        console.log('checking for updates' + new Date());
        this.checkForUpdates();
      }, 3000);
    });
  }

  private checkForUpdates() {
    this.apiClient.getContract(this.id).subscribe(
      (data: Contract ) => {
        this.contract = data;
        this.extractResults(data);
        this.isLoading = false;
      },
      error => {
        console.log(error);
        this.errorModalService.showDefaultError();
        this.isLoading = false;
      }
    );
  }

  private extractResults(data: Contract) {
    // Extract results into our view data structure for rendering
    // todo: use labels from output schema instead of property name
    // todo: add support for multiple levels deep?

    const resultRows: Array<ResultRow> = [];
    const resultArrays: Array<ResultArray> = [];
    for (const key in data.results) {
      if (data.results.hasOwnProperty(key)) {
        const result = data.results[key];
        if (result instanceof Array) {
          for (let i = 0; i < result.length; i++) {
            const subResult = result[i];
            const nestedResultRows: Array<ResultRow> = [];
            for (const nestedKey in subResult) {
              if (subResult.hasOwnProperty(nestedKey)) {
                const nestedResult = subResult[nestedKey];
                nestedResultRows.push({
                  name: nestedKey,
                  value: nestedResult
                });
              }
            }
            resultArrays.push({
              name: key + '[' + i + ']',
              rows: nestedResultRows
            });
          }
        } else {
          resultRows.push({
            name: key,
            value: result
          });
        }
      }
    }
    this.resultRows = resultRows;
    this.resultArrays = resultArrays;
  }

  ngOnDestroy() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

}
