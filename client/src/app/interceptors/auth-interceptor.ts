import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {UserService} from "../user-profile/user.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private userService: UserService) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        if(this.userService.isAuthenticated){
            let headers: HttpHeaders = new HttpHeaders({
                'Authorization': 'Basic ' + sessionStorage.getItem('token')
            });
            req = req.clone({
                headers: headers
            });
        }

        return next.handle(req);
    }


}
