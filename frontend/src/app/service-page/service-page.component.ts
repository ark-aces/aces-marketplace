import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ApiClient, Service} from '../api-client/api-client.component';
import {AuthService} from '../auth/auth-service.component';
import {ErrorModalService} from '../app-components/error-modal-service.compoennt';

@Component({
  templateUrl: './service-page.component.html'
})
export class ServicePageComponent implements OnInit, OnDestroy {

  isLoadingService = true;
  isFailed = false;
  serviceId: string;
  service;

  isLoadingContractForm = false;
  serviceInfo;

  hasExchangeRate = false;
  isLoadingExchangeRate = false;
  exchangeFromSymbol: string;
  exchangeToSymbol: string;
  exchangeRate: number;

  updater;

  private useStubData = false;
  private stub = {
    'name' : 'Testnet Aces ARK-BTC Channel Service',
    'description' : 'Testnet ACES ARK to BTC Channel service for value transfers',
    'version' : '1.0.0',
    'websiteUrl' : 'https://arkaces.com',
    'instructions' : 'After this contract is executed, any ARK sent to depositArkAddress will be exchanged for BTC and sent directly to the given recipientBtcAddress less service fees.\n',
    'capacities' : [ {
      'value' : 50.00,
      'unit' : 'BTC',
      'displayValue' : '50 BTC'
    } ],
    'flatFee' : '0',
    'percentFee' : '0.00%',
    'inputSchema' : {
      'type' : 'object',
      'properties' : {
        'recipientBtcAddress' : {
          'title': 'Recipient BTC Address',
          'description': 'Enter a valid BTC address to receive transfers',
          'type' : 'string'
        }
      },
      'required' : [ 'recipientBtcAddress' ]
    },
    'outputSchema' : {
      'type' : 'object',
      'properties' : {
        'depositArkAddress' : {
          'type' : 'string'
        },
        'recipientBtcAddress' : {
          'type' : 'string'
        },
        'transfers' : {
          'type' : 'array',
          'properties' : {
            'arkAmount' : {
              'type' : 'string'
            },
            'arkToBtcRate' : {
              'type' : 'string'
            },
            'arkFlatFee' : {
              'type' : 'string'
            },
            'arkPercentFee' : {
              'type' : 'string'
            },
            'arkTotalFee' : {
              'type' : 'string'
            },
            'btcSendAmount' : {
              'type' : 'string'
            },
            'btcTransactionId' : {
              'type' : 'string'
            },
            'createdAt' : {
              'type' : 'string'
            }
          }
        }
      }
    }
  };

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private apiClient: ApiClient,
    private errorModalService: ErrorModalService
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.serviceId = params['id'];
      this.apiClient.getServiceInfo(this.serviceId).subscribe(
        response => {
          this.service = response;
          if (response['exchangeRateHref'] != null) {
            this.hasExchangeRate = true;
            this.isLoadingExchangeRate = true;
            this.apiClient.getExchangeRate(this.serviceId).subscribe(
              exchangeRateResponse => {
                this.exchangeFromSymbol = exchangeRateResponse['from'];
                this.exchangeToSymbol = exchangeRateResponse['to'];
                this.exchangeRate = exchangeRateResponse['rate'];
                this.isLoadingExchangeRate = false;
              },
              error => {
                console.log(error);
                this.isLoadingExchangeRate = false;
              }
            );
          }

          this.isLoadingService = false;
        },
        error => {
          console.log(error);
          this.errorModalService.showDefaultError();
          this.isLoadingService = false;
        }
      );

        this.isLoadingContractForm = true;

        if (this.useStubData === true) {
          this.serviceInfo = this.stub;
        } else {
          this.apiClient.getServiceInfo(this.serviceId).subscribe(
            data => {
              this.serviceInfo = data;
              this.isLoadingContractForm = false;
            },
            error => {
              console.log(error);
              this.errorModalService.showError(
                'Service Unavailable',
                'Failed to fetch service info from remote service provider. ' +
                'This may be because the service is currently down or temporarily unavailable.\n' +
                'Please try again later or contact the service provider.'
                )
              this.isFailed = true;
              this.isLoadingContractForm = false;
            }
          );
        }

        if (this.updater == null) {
          this.updater = setInterval(() => {
            this.updateCapacity();
          }, 3000);
        }

    });
  }

  ngOnDestroy() {
    clearInterval(this.updater);
  }

  updateCapacity() {
    this.apiClient.getServiceInfo(this.serviceId).subscribe(
      data => {
        this.serviceInfo = data;
      },
      error => {
        console.log(error);
      }
    );
  }

  isLoading() {
      return this.isLoadingService || this.isLoadingContractForm;
  }

}
