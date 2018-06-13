import {Component} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {AuthService} from '../auth/auth-service.component';

@Component({
  selector: 'app-admin-sidebar-menu',
  templateUrl: 'admin-sidebar-menu.component.html'
})
export class AdminSidebarMenuComponent {

  topLevelRoute: string;

  constructor(public authService: AuthService, private router: Router, private activatedRoute: ActivatedRoute) {
    // Subscribe to route changes and assign the top-level route to the parentRoute variable
    this.router.events.subscribe(() => {
      const re = /\/|\?/;
      this.topLevelRoute = this.router.url.split(re)[2];
    });
  }

}
