import {Inject, Injectable, OnInit} from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {User} from '../api-client/api-client.component';
import {APP_CONFIG, AppConfig} from '../app-config-module';

@Injectable()
export class AuthService implements OnInit {

  public isAuthenticated = false;
  public user: User = null;
  public accessToken: string = null;

  constructor(
    @Inject(APP_CONFIG) private appConfig: AppConfig,
    private cookieService: CookieService
  ) {}

  ngOnInit(): void {
    this.loadCookie();
  }

  persistCookie(expireDate) {
    this.setCookie('isAuthenticated', this.isAuthenticated ? 'true' : 'false', expireDate);
    this.setCookie('user', JSON.stringify(this.user), expireDate);
    this.setCookie('accessToken', this.accessToken, expireDate);
  }

  private setCookie(name: string, value: string, expireDate) {
    this.cookieService.set(name, value, expireDate, '/', this.appConfig.cookieDomain, this.appConfig.useSecureCookie);
  }

  private deleteCookie(name: string) {
    this.cookieService.delete(name, '/', this.appConfig.cookieDomain);
  }

  loadCookie() {
    this.isAuthenticated = this.cookieService.get('isAuthenticated') === 'true';
    this.user = JSON.parse(this.cookieService.get('user'));
    this.accessToken = this.cookieService.get('accessToken');
  }

  logout() {
    this.isAuthenticated = false;
    this.user = null;
    this.accessToken = null;
    this.deleteCookie('isAuthenticated');
    this.deleteCookie('user');
    this.deleteCookie('accessToken');
    this.persistCookie(null);
  }
}
