import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../user.service";
import {User} from "../user.model.";
import {NotificationsService, NotificationType} from "../../notifications/notifications.service";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  public form: FormGroup = new FormGroup({
    "username": new FormControl("", [Validators.required]),
    "password": new FormControl("", [Validators.required]),
    "admin": new FormControl(true, [Validators.required]),
  })

  constructor(private router: Router,
              private userService: UserService,
              private notification: NotificationsService) { }

  ngOnInit(): void {
  }

  public save(): void {

    const user: User = {
      id: null,
      username: this.form.value.username,
      password: btoa(this.form.value.password),
      admin: this.form.value.admin
    };

    this.userService.saveUser(user).subscribe(
        (res: User) => {
          this.notification.showNotification("User Added successfully.", NotificationType.SUCCESS);
          this.router.navigate(["/user-profile"]);
        })
  }

  public onCancel(): void{
    this.router.navigate(["/user-profile"]);
  }

}
