import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AdminLayoutRoutes} from './admin-layout.routing';
import {UserProfileComponent} from '../../user-profile/user-profile.component';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatNativeDateModule, MatRippleModule} from '@angular/material/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSelectModule} from '@angular/material/select';
import {ItemsComponent} from "../../items/items.component";
import {CreateItemComponent} from "../../items/create-item/create-item.component";
import {SaleComponent} from "../../sale/sale.component";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {HistoryComponent} from "../../history/history.component";
import {SaleDetailComponent} from "../../history/sale-detail/sale-detail.component";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {AddUserComponent} from "../../user-profile/add-user/add-user.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(AdminLayoutRoutes),
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatRippleModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        MatTooltipModule,
        MatAutocompleteModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatCheckboxModule
    ],
    declarations: [
        UserProfileComponent,
        ItemsComponent,
        CreateItemComponent,
        SaleComponent,
        HistoryComponent,
        SaleDetailComponent,
        AddUserComponent
    ]
})

export class AdminLayoutModule {
}
