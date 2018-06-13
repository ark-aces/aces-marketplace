import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth-service.component';
import {Observable} from 'rxjs/Observable';

@Component({
  templateUrl: './admin-layout.component.html',
  styleUrls: ['./admin-layout.component.scss']
})
export class AdminLayoutComponent implements OnInit {
  title = 'ACES Marketplace Admin';

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.authService.loadCookie();
    Observable.timer(5000, 5000).subscribe(() => {
      this.authService.checkSession();
    });
  }

}
