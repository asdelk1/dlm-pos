import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ItemService} from "../item.service";
import {Item} from "../item.model";
import {ActivatedRoute, Router} from "@angular/router";
import {NotificationsService, NotificationType} from "../../notifications/notifications.service";
import {Observable, Subject} from 'rxjs';
import {distinctUntilChanged, startWith} from 'rxjs/operators';

@Component({
    selector: 'app-create-item',
    templateUrl: './create-item.component.html',
    styleUrls: ['./create-item.component.css']
})
export class CreateItemComponent implements OnInit {

    public form: FormGroup = new FormGroup({
        "id": new FormControl(""),
        "itemId": new FormControl("", [Validators.required]),
        "name": new FormControl("", [Validators.required]),
        "description": new FormControl("", []),
        "unitPrice": new FormControl("", [Validators.required]),
        "active": new FormControl("true"),
        "type": new FormControl("", [Validators.required])
    });

    public isEditMode: boolean = false;

    private _filteredItemTypes$: Subject<string[]> = new Subject<string[]>();
    public filteredItemTypes$: Observable<string[]> = this._filteredItemTypes$.asObservable();

    private itemTypes: string[] = [];

    constructor(private itemService: ItemService,
                private routes: Router,
                private activatedRoutes: ActivatedRoute,
                private notifications: NotificationsService) {
    }

    ngOnInit(): void {
        const id: string | null = this.activatedRoutes.snapshot.paramMap.get('id');
        if (id) {
            this.isEditMode = true;
            this.itemService.getItem(id).subscribe(
                (item: Item) => {
                    this.form.patchValue(item);
                });
        }

        this.loadItemTypes();

    }

    public onSubmit(): void {
        console.log(this.form.value);
        const item: Item = this.form.value as Item;
        this.itemService.saveItem(item).subscribe((newItem: Item) => {
            this.notifications.showNotification("Item saved successfully.", NotificationType.SUCCESS);
            this.routes.navigate(["/items"]);
        });
    }

    public onCancel(): void {
        this.routes.navigate(["/items"]);
    }

    public onSelectionChange(): void {

    }

    public loadItemTypes(): void {
        this.itemService.listItemTypes().subscribe((types: string[]) => {
            this.itemTypes = types;
            this.form.get('type').valueChanges.pipe(
                startWith(''),
                distinctUntilChanged()
            ).subscribe(
                (value: string) => {
                    let filteredTypes: string[];
                    if (this.itemTypes) {
                        filteredTypes = this.itemTypes.filter((type: string) => type.includes(value));
                    } else {
                        filteredTypes = [];
                    }
                    this._filteredItemTypes$.next(filteredTypes);
                }
            );
        });
    }


}
