import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import { ClientsComponent } from './clients/clients.component';
import { ClientListComponent } from './clients/client-list/client-list.component';
import { ClientDetailComponent } from './clients/client-detail/client-detail.component';
import {ClientService} from './clients/shared/client.service';
import { ClientNewComponent } from './clients/client-new/client-new.component';
import { GunTypesComponent } from './gun-types/gun-types.component';
import { GunProvidersComponent } from './gun-providers/gun-providers.component';
import { GunProviderNewComponent } from './gun-providers/gun-provider-new/gun-provider-new.component';
import { GunProviderDetailComponent } from './gun-providers/gun-provider-detail/gun-provider-detail.component';
import { GunProviderListComponent } from './gun-providers/gun-provider-list/gun-provider-list.component';
import { GunTypeListComponent } from './gun-types/gun-type-list/gun-type-list.component';
import { GunTypeNewComponent } from './gun-types/gun-type-new/gun-type-new.component';
import { GunTypeDetailComponent } from './gun-types/gun-type-detail/gun-type-detail.component';
import { RentalsComponent } from './rentals/rentals.component';
import { RentalsListComponent } from './rentals/rentals-list/rentals-list.component';
import { RentalNewComponent } from './rentals/rental-new/rental-new.component';



@NgModule({
  declarations: [
    AppComponent,
    ClientsComponent,
    ClientListComponent,
    ClientDetailComponent,
    ClientNewComponent,
    GunTypesComponent,
    GunProvidersComponent,
    GunProviderNewComponent,
    GunProviderDetailComponent,
    GunProviderListComponent,
    GunTypeListComponent,
    GunTypeNewComponent,
    GunTypeDetailComponent,
    RentalsComponent,
    RentalsListComponent,
    RentalNewComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
  ],
  providers: [ClientService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
