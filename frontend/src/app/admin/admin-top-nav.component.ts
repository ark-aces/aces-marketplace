import {Component} from '@angular/core';
import {AuthService} from '../auth/auth-service.component';

@Component({
  selector: 'app-admin-top-nav',
  templateUrl: 'admin-top-nav.component.html'
})
export class AdminTopNavComponent {
  constructor(public authService: AuthService) {}
}
