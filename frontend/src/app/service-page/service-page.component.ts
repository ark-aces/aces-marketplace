import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {ApiClient, Service} from '../api-client/api-client.component';

@Component({
  templateUrl: './service-page.component.html'
})
export class ServicePageComponent implements OnInit, OnDestroy {

  id: string;
  private sub: any;
  service: Service;

  constructor(private route: ActivatedRoute, private apiClient: ApiClient) {}

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
      this.apiClient.getService(this.id).subscribe(data => {
        this.service = data;
      });
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
