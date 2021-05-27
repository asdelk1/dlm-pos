import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ApiService} from "../api/api.service";
import {Observable} from "rxjs";
import {User} from "./user.model.";
import "rxjs-compat/add/observable/of";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private _loggedInUser: User;

    public set loggedInUser(user: User) {
        if (!this._loggedInUser) {
            this._loggedInUser = user;
        }
    }

    public get loggedInUser(): User {
        return this._loggedInUser;
    }

    constructor(private http: HttpClient,
                private api: ApiService) {
    }

    public getUsers(): Observable<User[]> {
        return this.http.get<User[]>(this.api.getBaseURL() + "/users");
    }

    public saveUser(user: User): Observable<User> {

        const url: string = `${this.api.getBaseURL()}/save-user`;
        return this.http.post<User>(url, user);
    }

    public getUser(username: string, password: string): Observable<User> {

        //  const url: string = `${this.api.getBaseURL()}\users\${username}`;
        //  const options: any = {
        //      headers: new HttpHeaders({"Authorization": "Basic "+ btoa(username+":"+password)})
        //  };
        // return this.http.get<User>(url, options);

        const user = {
            id: 1,
            username: "admin",
            password: "Admin111",
            admin: true
        };

        return Observable.of(user);
    }
}
