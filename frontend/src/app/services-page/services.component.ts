import { Component, OnInit } from '@angular/core';
import {ApiClient, Service} from '../api-client/api-client.component';
import {ErrorModalService} from '../app-components/error-modal-service.compoennt';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.scss']
})
export class ServicesComponent implements OnInit {

  loading = true;
  services: Array<Service> = [];

  constructor(
    private apiClient: ApiClient,
    private errorModalService: ErrorModalService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.apiClient.getServices()
      .subscribe(
        response => {
          // todo: pagination
          console.log(response);
          this.services = response.items;
          this.loading = false;
        },
      error => {
          console.log(error);
        this.errorModalService.showDefaultError();
        this.loading = false;
      });
  }

}
