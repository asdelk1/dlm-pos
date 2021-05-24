import {Injectable} from '@angular/core';
import {ApiService} from "../api/api.service";
import {Sale, SaleDetail} from "./sale.model";
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

    public saveShoppingCart(receivedAmount: number, items: SaleDetail[]): Observable<string> {
        const sale: {moneyReceived: number, items: SaleDetail[]} = {
            moneyReceived: receivedAmount,
            items: items
        };
        return this.httpClient.post<string>(this.baseURL, sale);
    }
    
    public printReceipt(id: number): Observable<any>{

        const url: string = `${this.baseURL}/${id}/print`;
        return this.httpClient.get<any>(url,  {"responseType": 'arraybuffer' as "json" });
    }

    public getSale(id: number): Observable<Sale>{
        return this.httpClient.get<Sale>(this.baseURL+"/"+id);
    }

}
