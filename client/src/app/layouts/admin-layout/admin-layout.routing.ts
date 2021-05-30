import {Routes} from '@angular/router';

import {DashboardComponent} from '../../dashboard/dashboard.component';
import {UserProfileComponent} from '../../user-profile/user-profile.component';
import {TableListComponent} from '../../table-list/table-list.component';
import {TypographyComponent} from '../../typography/typography.component';
import {IconsComponent} from '../../icons/icons.component';
import {NotificationsComponent} from '../../notifications/notifications.component';
import {UpgradeComponent} from '../../upgrade/upgrade.component';
import {ItemsComponent} from "../../items/items.component";
import {CreateItemComponent} from "../../items/create-item/create-item.component";
import {SaleComponent} from "../../sale/sale.component";
import {HistoryComponent} from "../../history/history.component";
import {SaleDetailComponent} from "../../history/sale-detail/sale-detail.component";
import {AddUserComponent} from "../../user-profile/add-user/add-user.component";

export const AdminLayoutRoutes: Routes = [
    {path: 'dashboard', component: DashboardComponent},
    {
        path: 'user-profile',
    children: [
        {path: '', component: UserProfileComponent},
        {path: 'add', component: AddUserComponent}
    ]},
    {path: 'table-list', component: TableListComponent},
    {path: 'typography', component: TypographyComponent},
    {path: 'icons', component: IconsComponent},
    {path: 'notifications', component: NotificationsComponent},
    {path: 'upgrade', component: UpgradeComponent},
    {path: 'sale', children:[
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
];
