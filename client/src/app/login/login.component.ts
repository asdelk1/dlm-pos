import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../user-profile/user.service";
import {User} from "../user-profile/user.model.";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    public form: FormGroup = new FormGroup({
        "username": new FormControl("", [Validators.required]),
        "password": new FormControl("", [Validators.required])
    });

    constructor(private router: Router,
                private userService: UserService) {
    }

    ngOnInit(): void {
    }

    public login(): void {
        this.userService.getUser(this.form.get("username").value, this.form.get("password").value).subscribe(
            (user: User) =>{
                this.userService.loggedInUser = user;
                this.router.navigate([""]);
            },
            (error) => console.log(error)
        );
    }

}
