import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Sale} from "../sale/sale.model";
import {ApiService} from "../api/api.service";
import {HttpClient} from "@angular/common/http";
import {take} from "rxjs/operators";
import {DatePipe} from "@angular/common";

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
        let params: {[key: string]: string} | undefined = undefined;
        if(timeRange && (timeRange.start || timeRange.end)){
            const datePipe = new DatePipe('en-US');
            params = {};
            if(timeRange.start){
                params["start"] = datePipe.transform(timeRange.start, "yyyy-MM-dd");
            }

            if(timeRange.end){
                params["end"] = datePipe.transform(timeRange.end, "yyyy-MM-dd");
            }

        }

        this.httpService.get<Sale[]>(this.baseUrl,{params: params}).pipe(take(1)).subscribe(
            (sales: Sale[]) => {
                this.previousSales = sales;
            }
        )
    }

    public reset(): void {
        this.range.reset({});
        this.loadData()
    }

}
