import { Component, OnInit } from '@angular/core';
import {User} from "./user.model.";
import {UserService} from "./user.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  public users: User[];

  constructor(private userService: UserService) { }

  ngOnInit() {

    this.userService.getUsers().subscribe(
        (users: User[]) => this.users = users
    );
  }

}
