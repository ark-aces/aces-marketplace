import {Inject, Injectable, OnInit} from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {User} from '../api-client/api-client.component';
import {APP_CONFIG, AppConfig} from '../app-config-module';
import {ErrorModalService} from '../app-components/error-modal-service.compoennt';
import {Router} from '@angular/router';

@Injectable()
export class AuthService implements OnInit {

  public isAuthenticated = false;
  public user: User = null;
  public accessToken: string = null;

  constructor(
    @Inject(APP_CONFIG) private appConfig: AppConfig,
    private cookieService: CookieService,
    private errorModalService: ErrorModalService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCookie();
  }

  persistCookie(expireDate: Date) {
    this.setCookie('isAuthenticated', this.isAuthenticated ? 'true' : 'false', expireDate);
    this.setCookie('user', JSON.stringify(this.user), expireDate);
    this.setCookie('accessToken', this.accessToken, expireDate);
  }

  private setCookie(name: string, value: string, expireDate: Date) {
    this.cookieService.set(name, value, expireDate, '/', this.appConfig.cookieDomain, this.appConfig.useSecureCookie);
  }

  private deleteCookie(name: string) {
    this.cookieService.delete(name, '/', this.appConfig.cookieDomain);
  }

  loadCookie() {
    this.isAuthenticated = this.cookieService.get('isAuthenticated') === 'true';
    const userJson = this.cookieService.get('user');
    if (userJson) {
      this.user = JSON.parse(userJson);
    } else {
      this.user = null;
    }
    this.accessToken = this.cookieService.get('accessToken');
  }

  checkSession() {
    if (this.isAuthenticated && ! this.cookieService.get('accessToken')) {
      this.expireSession();
    }
  }

  expireSession() {
    this.logout();
    this.errorModalService.showError(
      'Session Expired',
      'Your session token has expired. Please sign in again.'
    );
    this.router.navigate(['/sign-in']);
  }

  logout() {
    this.isAuthenticated = false;
    this.user = null;
    this.accessToken = null;
    this.deleteCookie('isAuthenticated');
    this.deleteCookie('user');
    this.deleteCookie('accessToken');
  }
}
