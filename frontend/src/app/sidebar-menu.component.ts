import { Component } from '@angular/core';
import {AuthService} from './auth/auth-service.component';

@Component({
  selector: 'app-sidebar-menu',
  templateUrl: 'sidebar-menu.component.html'
})
export class SidebarMenuComponent {

  constructor(private authService: AuthService) {}

}
