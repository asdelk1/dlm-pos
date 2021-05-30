import {Injectable} from "@angular/core";
import {HttpClient, HttpEvent, HttpHeaders} from "@angular/common/http";
import {ApiService} from "../api/api.service";
import {Observable} from "rxjs";
import {User} from "./user.model.";
import "rxjs-compat/add/observable/of";
import {filter, tap} from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private _loggedInUser: User;
    private _isAuthenticated: boolean;

    public set loggedInUser(user: User) {
        if (!this._loggedInUser) {
            this._loggedInUser = user;
        }
    }

    public get loggedInUser(): User {
        return this._loggedInUser;
    }

    public set isAuthenticated(value: boolean){
        this._isAuthenticated = value;
    }

    public get isAuthenticated(): boolean {
        return this._isAuthenticated;
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

    public login(username: string, password: string): Observable<User | null>{
        const url: string = `${this.api.getBaseURL()}/login`;
        const encryptedPassword: string = btoa(password);

        const body: Object = {
            username: username,
            password: encryptedPassword
        };
        return this.http.post<User | null>(url, body).pipe(
            tap((res: User | null) => {
                if(res){
                    sessionStorage.setItem('token', btoa(username + ':' + encryptedPassword));
                    this.isAuthenticated = true;
                    this.loggedInUser = res;
                }
            })
        );
    }


}
