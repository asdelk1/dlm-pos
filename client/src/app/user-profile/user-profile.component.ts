import {Component, OnInit} from '@angular/core';
import {User} from "./user.model.";
import {UserService} from "./user.service";
import {NotificationsService, NotificationType} from "../notifications/notifications.service";

@Component({
    selector: 'app-user-profile',
    templateUrl: './user-profile.component.html',
    styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

    public users: User[];
    public isDeleteButtonVisible: boolean;

    constructor(private userService: UserService,
                private notifications: NotificationsService) {
    }

    ngOnInit() {
        this.isDeleteButtonVisible = this.userService.loggedInUser.admin;
        this.userService.getUsers().subscribe(
            (users: User[]) => this.users = users
        );
    }

    public deleteUser(user: User): void {

        this.userService.deleteUser(user).subscribe(
            () => {
                this.notifications.showNotification("User " + user.username + " is deleted successfully.", NotificationType.DANGER);
                this.ngOnInit();
            }
        )
    }

}
