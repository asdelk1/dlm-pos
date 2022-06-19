import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ItemService} from "./item.service";
import {Item} from "./item.model";
import {FormControl} from '@angular/forms';
import {distinctUntilChanged, throttleTime} from 'rxjs/operators';

@Component({
    selector: 'app-items',
    templateUrl: './items.component.html',
    styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {

    private _items: Item[];
    public items: Item[];
    public types: string[] = [];
    public selectedType: string | undefined = undefined;

    public itemSearch: FormControl = new FormControl('');

    @Input()
    public showCreate: boolean = true;

    @Input()
    public selectable: boolean = false;

    @Output()
    public itemSelected: EventEmitter<Item> = new EventEmitter<Item>();

    constructor(private router: Router,
                private itemService: ItemService,
                private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.itemService.listItems().subscribe((items: Item[]) => {
            this._items = items;
            this.items = items
        });

        this.itemSearch.valueChanges.pipe(
            distinctUntilChanged(),
            throttleTime(500)
        ).subscribe(
            (value: string) => {
                if(value.trim() !== ''){
                    this.items = this._items.filter((item: Item) => item.itemId.includes(value) || item.name.includes(value));
                }else{
                    this.items = this._items;
                }

            }
        );

        this.loadTypes();
    }

    public onItemActionClicked(item: Item) {
        if (this.selectable) {
            this.itemSelected.emit(item);
        } else {
            this.router.navigate([item.id], {relativeTo: this.route});
        }
    }

    public isTypeDisabled(type: string): boolean {

        // return this.selectedType !== undefined ? this.selectedType !== type : false;
        return this.selectedType !== type;
    }

    public onSelectType(type: string): void {
        if(this.selectedType === type){
            this.selectedType = undefined;
            this.items = this._items;
        }else{
            this.selectedType = type;
            this.items = this._items.filter((item: Item) => item.type === this.selectedType);
        }
    }

    private loadTypes(): void {
        this.itemService.listItemTypes().subscribe((types: string[]) => this.types = types);
    }

    public clearTypeSearch(): void {
        this.selectedType = undefined;
        this.items = this._items;
    }

}
