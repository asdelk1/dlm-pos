import {Item} from "../items/item.model";

export interface SaleDetail{
    id?: number;
    item: Item;
    qty: number;
    unitPrice?: number;
    total?: number;
}

export interface Sale {
    id: number;
    timestamp: Date;
    date: Date;
    total: number;
    amountReceived: number;
    balance: number;
    itemCount: number;
    details: SaleDetail[];
}
