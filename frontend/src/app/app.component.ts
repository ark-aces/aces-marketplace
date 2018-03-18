import {Component, OnInit} from '@angular/core';
import {AuthService} from './auth/auth-service.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'ACES Marketplace';

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.loadCookie();
  }
}
