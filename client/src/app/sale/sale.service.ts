import {Injectable} from '@angular/core';
import {ApiService} from "../api/api.service";
import {SaleDetail} from "./sale.model";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class SaleService {

    private readonly baseURL: string;

    constructor(private apiService: ApiService,
                private httpClient: HttpClient) {
        this.baseURL = `${apiService.getBaseURL()}/sale`;
    }

    public saveShoppingCart(items: SaleDetail[]): Observable<SaleDetail[]> {
        return this.httpClient.post<SaleDetail[]>(this.baseURL, items);
    }

}
