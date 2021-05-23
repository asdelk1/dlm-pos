import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Sale} from "../sale/sale.model";
import {ApiService} from "../api/api.service";
import {HttpClient} from "@angular/common/http";
import {take} from "rxjs/operators";

@Component({
    selector: 'app-history',
    templateUrl: './history.component.html',
    styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

    private baseUrl: string;
    public previousSales: Sale[] = [];

    public range: FormGroup = new FormGroup({
        start: new FormControl(),
        end: new FormControl()
    });

    constructor(private apiService: ApiService,
                private httpService: HttpClient) {
        this.baseUrl = `${this.apiService.getBaseURL()}/sale/history`;
    }

    ngOnInit(): void {
        this.loadData();
    }

    public loadData(): void {
        const timeRange: { start: Date, end: Date } = this.range.value;
        if(timeRange){
            console.log('this should load new data with time range.')
        }

        this.httpService.get<Sale[]>(this.baseUrl).pipe(take(1)).subscribe(
            (sales: Sale[]) => {
                this.previousSales = sales;
            }
        )
    }

}
