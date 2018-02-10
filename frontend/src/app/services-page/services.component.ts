import { Component, OnInit } from '@angular/core';
import { Service } from '../service';
import { SERVICES } from '../mock-services';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.scss']
})
export class ServicesComponent implements OnInit {

  service = SERVICES;

  selectedService: Service;

  onSelect(service: Service): void {
    this.selectedService = service;
  }

  constructor() { }

  ngOnInit() {
  }

}
