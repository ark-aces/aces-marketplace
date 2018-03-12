import {Component, OnInit} from '@angular/core';
import {ApiClient} from '../api-client/api-client.component';
import {ActivatedRoute} from '@angular/router';

@Component({
  templateUrl: './email-verification-page.component.html'
})
export class EmailVerificationPageComponent implements OnInit {

  successful = true;
  loading = true;

  constructor(private apiClient: ApiClient, private route: ActivatedRoute) { }

  ngOnInit() {
    const code = this.route.snapshot.queryParams['code'];
    console.log(code);
    this.apiClient
      .postEmailVerification({code: code})
      .subscribe(
        result => {
          this.successful = true;
          this.loading = false;
        },
        error => {
          this.successful = false;
          this.loading = false;
        }
      );
  }

}
