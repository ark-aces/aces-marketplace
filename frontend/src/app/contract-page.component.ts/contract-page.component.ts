import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ApiClient, Contract} from '../api-client/api-client.component';
import {ErrorModalService} from '../app-components/error-modal-service.compoennt';

class ResultRow {
  name: string;
  title?: string;
  description?: string;
  value: object;
}

class ResultArray {
  name: string;
  rows: Array<ResultRow>;
}

class ArkPaymentParams {
  arkSmartBridge: string;
  serviceArkAddress: string;
  requiredArk: string;
}

@Component({
  templateUrl: './contract-page.component.html'
})
export class ContractPageComponent implements OnInit, OnDestroy {

  id: string;
  contract: Contract;
  resultRows: Array<ResultRow> = [];
  resultArrays: Array<ResultArray> = [];

  isLoadingContractForm = true;
  isLoadingServiceInfo = true;
  serviceInfo;
  isFailed = false;

  interval: any;

  isArkPayable = false;
  arkPaymentParams: ArkPaymentParams;

  constructor(
    private route: ActivatedRoute,
    private apiClient: ApiClient,
    private errorModalService: ErrorModalService
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params['id'];

      this.checkForUpdates();

      // todo: re-enable real-time update when refresh is fixed
      // this.interval = setInterval(() => {
      //   this.checkForUpdates();
      // }, 3000);
    });
  }

  private checkForUpdates() {
    this.apiClient.getContract(this.id).subscribe(
      (data: Contract ) => {
        this.contract = data;

        this.apiClient.getServiceInfo(data.serviceId).subscribe(
          serviceInfo => {
            this.serviceInfo = serviceInfo;

            // todo: replace with service interface arkSmartBridgePayable
            const outputProperties = this.serviceInfo.outputSchema.properties;
            this.isArkPayable = outputProperties.hasOwnProperty('arkSmartBridge')
              && outputProperties.hasOwnProperty('serviceArkAddress')
              && outputProperties.hasOwnProperty('requiredArk');

            if (this.isArkPayable) {
              this.arkPaymentParams = {
                arkSmartBridge: data.results['arkSmartBridge'],
                serviceArkAddress: data.results['serviceArkAddress'],
                requiredArk: data.results['requiredArk']
              };
            }

            this.extractResults(data, serviceInfo);
            this.isLoadingContractForm = false;
            this.isLoadingServiceInfo = false;
            this.isLoadingServiceInfo = false;
          },
          error => {
            console.log(error);
            this.errorModalService.showError(
              'Service Unavailable',
              'Failed to fetch service info from remote service provider. ' +
              'This may be because the service is currently down or temporarily unavailable.\n' +
              'Please try again later or contact the service provider.'
            );
            this.isFailed = true;
            this.isLoadingServiceInfo = false;
          });


      },
      error => {
        console.log(error);
        this.errorModalService.showError(
          'Contract Unavailable',
          'Failed to fetch contract info from remote service provider. ' +
          'This may be because the service is currently down or the contract is no longer available.\n' +
          'Please try again later or contact the service provider.'
        );
        this.isFailed = true;
        this.isLoadingContractForm = false;
        this.isLoadingServiceInfo = false;
      }
    );
  }

  private extractResults(data: Contract, serviceInfo) {
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
                const parentSchema = serviceInfo.outputSchema.properties[key];
                const nestedResult = subResult[nestedKey];
                if (parentSchema.properties.hasOwnProperty(nestedKey)) {
                  const nestedSchema = parentSchema.properties[nestedKey];
                  nestedResultRows.push({
                    name: nestedKey,
                    title: nestedSchema.title,
                    description: nestedSchema.description,
                    value: nestedResult
                  });
                } else {
                  nestedResultRows.push({
                    name: nestedKey,
                    title: nestedKey,
                    description: nestedKey,
                    value: nestedResult
                  });
                }

              }
            }
            resultArrays.push({
              name: key + '[' + i + ']',
              rows: nestedResultRows
            });
          }
        } else {
          const fieldProperties = serviceInfo.outputSchema.properties[key];
          resultRows.push({
            name: key,
            title: fieldProperties.title,
            description: fieldProperties.description,
            value: result
          });
        }
      }
    }
    this.resultRows = resultRows;
    this.resultArrays = resultArrays;
  }

  isLoading() {
    return this.isLoadingContractForm || this.isLoadingServiceInfo;
  }

  ngOnDestroy() {
    if (this.interval) {
      clearInterval(this.interval);
    }
  }

}
