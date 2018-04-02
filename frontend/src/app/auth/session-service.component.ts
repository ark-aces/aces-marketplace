import {Injectable, OnInit} from '@angular/core';
import {ApiClient} from '../api-client/api-client.component';
import {ErrorModalService} from '../app-components/error-modal-service.compoennt';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {AuthService} from './auth-service.component';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class SessionService implements OnInit {

  constructor(
    private errorModalService: ErrorModalService,
    private router: Router,
    private apiClient: ApiClient,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    Observable.timer(5000, 5000).subscribe(() => {
      this.checkSession();
    });
  }

  checkSession() {
    this.apiClient.getCurrentUser().subscribe(() => {},
      (error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.showSessionTimedOut();
        }
      });
  }

  showSessionTimedOut() {
    this.authService.logout();
    this.errorModalService.showError(
      'Session Expired',
      'Your session token has expired. Please sign in again.'
    );
    this.router.navigate(['/sign-in']);
  }

}
