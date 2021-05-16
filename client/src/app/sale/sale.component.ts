import {Component, OnInit} from '@angular/core';
import {ItemService} from "../items/item.service";
import {Item} from "../items/item.model";
import {SaleDetail} from "./sale.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from 'rxjs/operators';
import {SaleService} from "./sale.service";
import {NotificationsService, NotificationType} from "../notifications/notifications.service";

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

    public form: FormGroup = new FormGroup({
        "item": new FormControl("", [Validators.required]),
        "qty": new FormControl("", [Validators.required])
    });

    constructor(private itemService: ItemService,
                private saleService: SaleService,
                private notificationService: NotificationsService
    ) {

    }

    ngOnInit(): void {
        this.itemService.listItems().subscribe(
            (items: Item[]) => {
                this.items = items;
                this.items.forEach((i) => this.itemDict[i.itemId] = i);
            }
        )

        this.form.get("item").valueChanges.subscribe(
            (value => console.log(value)));

        this.filteredItems = this.form.get("item").valueChanges
            .pipe(
                startWith(''),
                map(item => {
                    return item ? this._filterItems(item) : this.items.slice();
                })
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

    public onShoppingCart(): void {
        this.saleService.saveShoppingCart(this.sales).subscribe(
            (items: SaleDetail[]) => {
                this.notificationService.showNotification("Success", NotificationType.SUCCESS);
                this.resetCart();
            });
    }

}
