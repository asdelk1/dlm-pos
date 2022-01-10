import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Item} from "./item.model";
import {ApiService} from "../api/api.service";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class ItemService {

    private readonly baseURL: string = "";

    constructor(private httpClient: HttpClient,
                private apiService: ApiService) {
        this.baseURL = this.apiService.getBaseURL() + "/items";
    }

    public listItems(): Observable<Item[]> {
        return this.httpClient.get<Item[]>(this.baseURL);
    }

    public saveItem(item: Item): Observable<Item> {

        return this.httpClient.post<Item>(this.baseURL, item);
    }

    public getItem(id: string): Observable<Item>{

        const url: string = `${this.baseURL}/${id}`;
        return this.httpClient.get<Item>(url);
    }

    public listItemTypes(): Observable<string[]> {
        return this.httpClient.get<string[]>(this.baseURL + '/item-types');
    }
}
