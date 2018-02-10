import { Component } from '@angular/core';

@Component({
  selector: 'app-top-nav',
  template: `
    <div class='page-topbar '>

      <div class='quick-area'>
        <div class='pull-left'>
          <ul class="info-menu left-links list-inline list-unstyled">
            <li class="notify-toggle-wrapper">
              <a href="#" >
                <img src="images/logo-red.png" class="d-none d-lg-inline mr-2" style="width: 40px">
                <span style="margin-right: 2em; margin-left: 0.5em; font-size: 18px; font-weight: bold; text-decoration:none; color: white; text-transform: none">ACES Marketplace</span>
              </a>
              <a href="#" data-toggle="dropdown" class="toggle">
                <i class="fa fa-bell"></i>
                <span class="badge badge-accent">3</span>
              </a>
              <ul class="dropdown-menu notifications animated fadeIn">
                <li class="total">
                            <span class="small">
                                You have <strong>3</strong> new notifications.
                                <a href="javascript:;" class="pull-right">Mark all as Read</a>
                            </span>
                </li>
                <li class="list">
                  <ul class="dropdown-menu-list list-unstyled ps-scrollbar">
                    <li class="unread available"> <!-- available: success, warning, info, error -->
                      <a href="javascript:;">
                        <div class="notice-icon">
                          <i class="fa fa-check"></i>
                        </div>
                        <div>
                                                <span class="name">
                                                    <strong>Server needs to reboot</strong>
                                                    <span class="time small">15 mins ago</span>
                                                </span>
                        </div>
                      </a>
                    </li>
                    <li class="unread away"> <!-- available: success, warning, info, error -->
                      <a href="javascript:;">
                        <div class="notice-icon">
                          <i class="fa fa-envelope"></i>
                        </div>
                        <div>
                                                <span class="name">
                                                    <strong>45 new messages</strong>
                                                    <span class="time small">45 mins ago</span>
                                                </span>
                        </div>
                      </a>
                    </li>
                  </ul>
                </li>
              </ul>
            </li>
            <li class="hidden-sm hidden-xs searchform">
              <form action="ui-search.html" method="post">
                <div class="input-group">
                            <span class="input-group-addon">
                                <i class="fa fa-search"></i>
                            </span>
                  <input type="text" class="form-control animated fadeIn" placeholder="Search & Enter">
                </div>
                <input type='submit' value="">
              </form>
            </li>
          </ul>
        </div>
        <div class='pull-right'>
          <ul class="info-menu right-links list-inline list-unstyled">
            <li class="profile">
              <a href="#" data-toggle="dropdown" class="toggle">
                <span>John Smith <i class="fa fa-angle-down"></i></span>
              </a>
              <ul class="dropdown-menu profile animated fadeIn">
                <li>
                  <a href="#settings">
                    <i class="fa fa-wrench"></i>
                    Settings
                  </a>
                </li>
                <li>
                  <a href="#profile">
                    <i class="fa fa-user"></i>
                    Profile
                  </a>
                </li>
                <li>
                  <a href="#help">
                    <i class="fa fa-info"></i>
                    Help
                  </a>
                </li>
                <li class="last">
                  <a href="ui-login.html">
                    <i class="fa fa-lock"></i>
                    Logout
                  </a>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>

    </div>
  `
})
export class TopNavComponent {
}
