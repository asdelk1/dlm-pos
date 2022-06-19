import {Routes} from '@angular/router';
import {UserProfileComponent} from '../../user-profile/user-profile.component';
import {ItemsComponent} from "../../items/items.component";
import {CreateItemComponent} from "../../items/create-item/create-item.component";
import {SaleComponent} from "../../sale/sale.component";
import {HistoryComponent} from "../../history/history.component";
import {SaleDetailComponent} from "../../history/sale-detail/sale-detail.component";
import {AddUserComponent} from "../../user-profile/add-user/add-user.component";
import {BackupUpComponent} from '../../backup-up/backup-up.component';

export const AdminLayoutRoutes: Routes = [
    {
        path: 'user-profile',
        children: [
            {path: '', component: UserProfileComponent},
            {path: 'add', component: AddUserComponent}
        ]
    },
    {
        path: 'sale', children: [
            {path: '', component: SaleComponent}
        ]
    },
    {
        path: 'items',
        children: [
            {path: '', component: ItemsComponent},
            {path: 'add', pathMatch: 'full', component: CreateItemComponent},
            {path: ':id', component: CreateItemComponent}
        ]
    },
    {
        path: 'history',
        children: [
            {path: '', component: HistoryComponent},
            {path: ':id', component: SaleDetailComponent}
        ]
    },
    {
        path: 'backup',
        children: [
            {path: '', component: BackupUpComponent},
        ]
    },
];
