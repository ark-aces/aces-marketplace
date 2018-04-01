import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ApiClient, Contract} from '../api-client/api-client.component';
import {ErrorModalService} from '../app-components/error-modal-service.compoennt';

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
export class ContractPageComponent implements OnInit {

  id: string;
  contract: Contract;
  resultRows: Array<ResultRow> = [];
  resultArrays: Array<ResultArray> = [];
  isLoading = true;

  constructor(
    private route: ActivatedRoute,
    private apiClient: ApiClient,
    private errorModalService: ErrorModalService
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params['id'];

      this.apiClient.getContract(this.id).subscribe(
        data => {
          this.contract = data;

          // Extract results into our view data structure for rendering
          // todo: use labels from output schema instead of property name
          // todo: add support for multiple levels deep?
          for (const key in this.contract.results) {
            if (this.contract.results.hasOwnProperty(key)) {
              const result = this.contract.results[key];
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
                  this.resultArrays.push({
                    name: key + '[' + i + ']',
                    rows: nestedResultRows
                  });
                }
              } else {
                this.resultRows.push({
                  name: key,
                  value: result
                });
              }
            }
          }
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

}
