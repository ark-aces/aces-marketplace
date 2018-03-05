import { Component, OnInit } from '@angular/core';
import {ApiClient, Service} from '../api-client/api-client.component';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.scss']
})
export class ServicesComponent implements OnInit {

  servicesPage;
  services: Array<Service> = [];

  constructor(private apiClient: ApiClient) { }

  ngOnInit() {
    this.apiClient.getServices()
      .subscribe(response => {
        this.services = response.items;
        console.log(response.items);
        // todo: pagination
        // todo: error modal
      } );
  }

}
