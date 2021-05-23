import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ItemService} from "../item.service";
import {Item} from "../item.model";
import {ActivatedRoute, Router} from "@angular/router";
import {NotificationsService, NotificationType} from "../../notifications/notifications.service";

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
    "description": new FormControl("", [Validators.required]),
    "unitPrice": new FormControl("", [Validators.required]),
    "active": new FormControl("true"),
  });

  public isEditMode: boolean = false;

  constructor(private itemService: ItemService,
              private routes: Router,
              private activatedRoutes: ActivatedRoute,
              private notifications: NotificationsService) { }

  ngOnInit(): void {
    const id: string | null = this.activatedRoutes.snapshot.paramMap.get('id');
    if(id){
      this.isEditMode = true;
      this.itemService.getItem(id).subscribe(
          (item: Item) => {
        this.form.patchValue(item);
      });
    }
  }

  public onSubmit(): void {
    console.log(this.form.value);
    const item: Item = this.form.value as Item;
    this.itemService.saveItem(item).subscribe((newItem: Item) => {
      this.notifications.showNotification("New Item saved successfully.", NotificationType.SUCCESS);
      this.routes.navigate(["/items"]);
    });
  }


}
