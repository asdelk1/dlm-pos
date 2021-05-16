import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {ItemService} from "./item.service";
import {Item} from "./item.model";

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {

  public items: Item[];

  constructor(private router: Router,
              private itemService: ItemService) { }

  ngOnInit(): void {
    this.itemService.listItems().subscribe((items: Item[]) => this.items = items);
  }


}
