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
        const username: string = this.form.value['username'];
        const password: string = this.form.value['password'];
        this.userService.login(username, password).subscribe(
            (user: User | null  ) => {
                if(user){
                    this.router.navigate(["/"])
                }else {
                    this.userService.isAuthenticated = false;
                    alert('Authentication is failed');
                }
            },
            (error => console.log('here is my error ' + error))
        )
    }

}
