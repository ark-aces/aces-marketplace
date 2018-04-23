import { Component, Input } from '@angular/core';
import {Capacity} from '../api-client/api-client.component';

@Component({
  selector: 'app-service-capacity-box',
  template: `
    <div class="capacity-box panel panel-default" *ngFor="let capacity of capacities">
      <div class="panel-heading">Capacity</div>
      <div class="panel-body">
        {{ capacity.value }}
        {{ capacity.unit }}
      </div>
    </div>
  `
})

export class ServiceCapacityBoxComponent {
  @Input() capacities: Array<Capacity>;
}
