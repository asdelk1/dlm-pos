import {Component, OnInit} from '@angular/core';
import {Sale} from "../../sale/sale.model";
import {FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {SaleService} from "../../sale/sale.service";
import {take} from "rxjs/operators";
import {DatePipe} from "@angular/common";

@Component({
    selector: 'app-sale-detail',
    templateUrl: './sale-detail.component.html',
    styleUrls: ['./sale-detail.component.css']
})
export class SaleDetailComponent implements OnInit {

    public sale: Sale;

    public idControl: FormControl;
    public timestampControl: FormControl;
    public totalControl: FormControl;
    public amountReceivedControl: FormControl;
    public balanceControl: FormControl;

    constructor(private activatedRoute: ActivatedRoute,
                private saleService: SaleService) {

        const datePipe: DatePipe = new DatePipe("en-US");
        const saleId: number = +this.activatedRoute.snapshot.paramMap.get("id");

        this.saleService.getSale(saleId).pipe(take(1)).subscribe((sale: Sale) => {
            this.sale = sale;
            this.idControl = new FormControl({value: sale.id, disabled: true});
            this.timestampControl = new FormControl({
                value: datePipe.transform(sale.timestamp, "yyyy-MM-dd hh:mm a"),
                disabled: true
            });
            this.totalControl = new FormControl({value: sale.total, disabled: true});
            this.amountReceivedControl = new FormControl({value: sale.amountReceived, disabled: true});
            this.balanceControl = new FormControl({value: sale.balance, disabled: true});
        })
    }

    ngOnInit(): void {
    }

}
