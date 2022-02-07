import { Component, OnInit } from '@angular/core';
import {UserService} from "../../user-profile/user.service";
import {User} from "../../user-profile/user.model.";

declare const $: any;
declare interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
    adminOnly: boolean;
}
export const ROUTES: RouteInfo[] = [
    { path: '/sale', title: 'Sale',  icon: 'shopping_cart', class: '', adminOnly: false  },
    { path: '/items', title: 'Items',  icon: 'dns', class: '', adminOnly: true  },
    { path: '/history', title: 'Previous Sales',  icon: 'restore', class: '', adminOnly: true  },
    { path: '/user-profile', title: 'User Profile',  icon:'person', class: '', adminOnly: true  },
    { path: '/backup', title: 'Backup Data',  icon:'add_to_drive', class: '', adminOnly: true  },
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  menuItems: any[];

  constructor(private userService: UserService) { }

  ngOnInit() {

      const loggedInUser: User = this.userService.loggedInUser;
    this.menuItems = ROUTES.filter(menuItem => !menuItem.adminOnly || (menuItem.adminOnly && loggedInUser.admin));
  }
  isMobileMenu() {
      if ($(window).width() > 991) {
          return false;
      }
      return true;
  };
}
