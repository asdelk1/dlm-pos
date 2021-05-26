import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ApiService} from "../api/api.service";
import {Observable} from "rxjs";
import {User} from "./user.model.";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient,
                private api: ApiService) {
    }

    public getUsers(): Observable<User[]>{
        return this.http.get<User[]>(this.api.getBaseURL()+"/users");
    }

    public saveUser(user: User): Observable<User>{

        const url: string = `${this.api.getBaseURL()}/save-user`;
        return this.http.post<User>(url, user);
    }
}
