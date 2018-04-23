import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-service-fees-box',
  template: `
    <div class="fees-box panel panel-default">
      <div class="panel-heading">Flat Fee</div>
      <div class="panel-body">
        {{ flatFee }}
      </div>
    </div>
  `
})

export class ServiceFeesBoxComponent {
  @Input() flatFee: number;
}
