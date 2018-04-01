import {Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {AuthService} from './auth/auth-service.component';


@Component({
  selector: 'app-sidebar-menu',
  templateUrl: 'sidebar-menu.component.html'
})
export class SidebarMenuComponent implements OnInit {

  topLevelRoute: string;

  constructor(public authService: AuthService, private router: Router, private activatedRoute: ActivatedRoute) {
    // Subscribe to route changes and assign the top-level route to the parentRoute variable
    this.router.events.subscribe(() => {
      const re = /\/|\?/;
      this.topLevelRoute = this.router.url.split(re)[1];
    });
  }

  ngOnInit(){
    // Optional console log for parentRoute
    console.log(this.topLevelRoute);
  }


}
