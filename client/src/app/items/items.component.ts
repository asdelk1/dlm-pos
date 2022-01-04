import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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

  @Input()
  public showCreate: boolean = true;

  @Input()
  public selectable: boolean = false;

  @Output()
  public itemSelected: EventEmitter<Item> = new EventEmitter<Item>();

  constructor(private router: Router,
              private itemService: ItemService) { }

  ngOnInit(): void {
    this.itemService.listItems().subscribe((items: Item[]) => this.items = items);
  }

  public onItemActionClicked(item: Item){
    if(this.selectable){
      this.itemSelected.emit(item);
    }else{
      this.router.navigate([item.id]);
    }
  }

}
