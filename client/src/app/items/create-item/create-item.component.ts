import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ItemService} from "../item.service";
import {Item} from "../item.model";
import {Router, Routes} from "@angular/router";

@Component({
  selector: 'app-create-item',
  templateUrl: './create-item.component.html',
  styleUrls: ['./create-item.component.css']
})
export class CreateItemComponent implements OnInit {

  public form: FormGroup = new FormGroup({
    "itemId": new FormControl("", [Validators.required]),
    "name": new FormControl("", [Validators.required]),
    "description": new FormControl("", [Validators.required]),
    "unitPrice": new FormControl("", [Validators.required]),
  });

  constructor(private itemService: ItemService,
              private routes: Router) { }

  ngOnInit(): void {
  }

  public onSubmit(): void {
    console.log(this.form.value);
    const item: Item = this.form.value as Item;
    this.itemService.saveItem(item).subscribe((newItem: Item) => {
      this.routes.navigate(["/items"]);
    });
  }


}
