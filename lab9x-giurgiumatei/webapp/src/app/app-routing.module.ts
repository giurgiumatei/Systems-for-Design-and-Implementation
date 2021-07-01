import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ClientsComponent} from './clients/clients.component';
import {ClientNewComponent} from './clients/client-new/client-new.component';
import {ClientDetailComponent} from './clients/client-detail/client-detail.component';
import {GunProvidersComponent} from './gun-providers/gun-providers.component';
import {GunProviderDetailComponent} from './gun-providers/gun-provider-detail/gun-provider-detail.component';
import {GunProviderNewComponent} from './gun-providers/gun-provider-new/gun-provider-new.component';
import {GunTypesComponent} from './gun-types/gun-types.component';
import {GunTypeDetailComponent} from './gun-types/gun-type-detail/gun-type-detail.component';
import {GunTypeNewComponent} from './gun-types/gun-type-new/gun-type-new.component';
import {RentalsComponent} from './rentals/rentals.component';
import {RentalNewComponent} from './rentals/rental-new/rental-new.component';


const routes: Routes = [
  {path: 'clients', component: ClientsComponent},
  {path: 'client/detail/:id', component: ClientDetailComponent},
  {path: 'client-new', component: ClientNewComponent},
  {path: 'gun-providers', component: GunProvidersComponent},
  {path: 'gun-provider/detail/:id', component: GunProviderDetailComponent},
  {path: 'gun-provider-new', component: GunProviderNewComponent},
  {path: 'gun-types', component: GunTypesComponent},
  {path: 'gun-type/detail/:id', component: GunTypeDetailComponent},
  {path: 'gun-type-new', component: GunTypeNewComponent},
  {path: 'rentals', component: RentalsComponent},
  {path: 'rentals-new', component: RentalNewComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
