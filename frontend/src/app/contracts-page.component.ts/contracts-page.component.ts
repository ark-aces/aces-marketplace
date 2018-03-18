import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Subscription';

@Component({
  templateUrl: './contracts-page.component.html'
})
export class ContractsPageComponent implements OnInit, OnDestroy {
  status = 'Active';
  sub: Subscription;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.status = params['status'].charAt(0).toUpperCase() + params['status'].slice(1);
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
