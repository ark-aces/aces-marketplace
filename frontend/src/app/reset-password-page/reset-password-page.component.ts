import {Component, OnInit} from '@angular/core';
import {ApiClient} from '../api-client/api-client.component';
import {ActivatedRoute} from '@angular/router';

@Component({
  templateUrl: './reset-password-page.component.html'
})
export class ResetPasswordPageComponent implements OnInit {

  loading = true;
  successful = false;
  code: string;

  constructor(private apiClient: ApiClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.code = this.route.snapshot.queryParams['code'];
  }

}
