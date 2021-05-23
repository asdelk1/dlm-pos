import {Item} from "../items/item.model";

export interface SaleDetail{
    item: Item;
    qty: number;
}

export interface Sale {
    id: number;
    timestamp: Date;
    total: number;
    amountReceived: number;
    balance: number;
    itemCount: number;
}
