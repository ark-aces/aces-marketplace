import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-service-percent-fees-box',
  template: `
    <div class="fees-box panel panel-default">
      <div class="panel-heading">Percent Fee</div>
      <div class="panel-body">
        {{ percentFee }}%
      </div>
    </div>
  `
})

export class ServicePercentFeesBoxComponent {
  @Input() percentFee: number;
}
