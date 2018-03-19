import {Component} from '@angular/core';
import {AuthService} from './auth/auth-service.component';

@Component({
  selector: 'app-top-nav',
  templateUrl: 'top-nav.component.html'
})
export class TopNavComponent {
  constructor(public authService: AuthService) {}
}
