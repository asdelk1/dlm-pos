import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ItemService} from "../items/item.service";
import {Item} from "../items/item.model";
import {SaleDetail} from "./sale.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith, switchMap, take} from 'rxjs/operators';
import {SaleService} from "./sale.service";
import {NotificationsService, NotificationType} from "../notifications/notifications.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-sale',
    templateUrl: './sale.component.html',
    styleUrls: ['./sale.component.css']
})
export class SaleComponent implements OnInit {

    public items: Item[] = [];
    public filteredItems: Observable<Item[]>;
    public itemDict: { [itemId: string]: Item } = {};
    public sales: SaleDetail[] = [];
    public total: number = 0.00;
    public balance: number = 0;

    @ViewChild("modalData")
    public saleDialog: ElementRef;

    public form: FormGroup = new FormGroup({
        "item": new FormControl("", [Validators.required]),
        "qty": new FormControl("", [Validators.required])
    });
    public receivedAmount: FormControl = new FormControl("", [Validators.required]);

    constructor(private itemService: ItemService,
                private saleService: SaleService,
                private notificationService: NotificationsService,
                private modalService: NgbModal
    ) {

    }

    ngOnInit(): void {
        this.itemService.listItems().subscribe(
            (items: Item[]) => {
                this.items = items;
                this.items.forEach((i) => this.itemDict[i.itemId] = i);
            }
        )

        this.filteredItems = this.form.get("item").valueChanges
            .pipe(
                startWith(''),
                map(item => {
                    return item ? this._filterItems(item) : this.items.slice();
                })
            );

        this.receivedAmount.valueChanges.subscribe(
            (value: number) => {
                this.balance = value - this.total;
            }
        );
    }

    private _filterItems(value: string): Item[] {

        const filterValue = value.toLowerCase();
        return this.items.filter(item => item.name.toLowerCase().indexOf(filterValue) === 0 || item.itemId.toLowerCase().indexOf(filterValue) === 0);
    }

    public onSelectionChange(): void {
        this.form.get("qty").setValue(1);
    }

    public resetCart(): void {
        this.total = 0.00;
        this.sales = [];
        this.receivedAmount.reset("");
    }

    public addToCart(): void {

        const obj: { item: string, qty: number } = this.form.value;
        const item: Item = this.itemDict[obj.item];
        this.sales.push({
            item: item,
            qty: obj.qty
        });

        this.total += item.unitPrice * obj.qty;
        this.form.reset({item: "", qty: ""});
    }

    public processCart(): void {
        this.modalService.open(this.saleDialog, {ariaLabelledBy: 'modal-basic-title'}).result.then(
            (res: string) => {
                this.receiveMoney();
            },
            (res: string) => {
                this.resetCart();
            }
        );
    }

    public receiveMoney(): void {

        this.saleService.saveShoppingCart(this.receivedAmount.value, this.sales).pipe(take(1),
            switchMap((sale: any) => {
                this.notificationService.showNotification("Success", NotificationType.SUCCESS);
                this.resetCart();
                return this.saleService.printReceipt(sale.id);
            }),
            take(1)
        ).subscribe(
            (response: any) => {
                let blob = new Blob([response], {type: "application/pdf"});
                let url = window.URL.createObjectURL(blob);
                let pwa = window.open(url);
                pwa.print();
            });
    }
}
