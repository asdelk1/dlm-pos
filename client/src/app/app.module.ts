import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';


import {AppRoutingModule} from './app.routing';
import {ComponentsModule} from './components/components.module';

import {AppComponent} from './app.component';
import {AgmCoreModule} from '@agm/core';
import {AdminLayoutComponent} from './layouts/admin-layout/admin-layout.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { SaleDetailComponent } from './history/sale-detail/sale-detail.component';


@NgModule({
    imports: [
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        ComponentsModule,
        RouterModule,
        AppRoutingModule,
        HttpClientModule,
        AgmCoreModule.forRoot({
            apiKey: 'YOUR_GOOGLE_MAPS_API_KEY'
        }),
        NgbModule
    ],
    declarations: [
        AppComponent,
        AdminLayoutComponent
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
