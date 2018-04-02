import {
  HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,
  HttpResponse
} from '@angular/common/http';
import {AuthService} from './auth-service.component';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(
    public authService: AuthService
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request)
      .map((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          return event;
        }
      })
      .catch((error: any) => {
        console.log('caught token error:');
        console.log(error);
        if (error instanceof HttpErrorResponse) {
          if (error.status === 401) {
            this.authService.expireSession();
          }
        } else {
          return Observable.throw(error);
        }
      });
  }
}
