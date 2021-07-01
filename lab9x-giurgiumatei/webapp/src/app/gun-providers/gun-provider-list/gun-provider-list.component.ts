import { Component, OnInit } from '@angular/core';
import {GunProvider} from '../shared/gun-provider.model';
import {GunProviderService} from '../shared/gun-provider.service';
import {Router, Routes} from '@angular/router';

@Component({
  selector: 'app-gun-provider-list',
  templateUrl: './gun-provider-list.component.html',
  styleUrls: ['./gun-provider-list.component.css']
})
export class GunProviderListComponent implements OnInit {

  gunProviders: GunProvider[];
  gunProvidersSorted: GunProvider[];
  selectedGunProvider: GunProvider;
  sortClicked: boolean;


  constructor(private gunProviderService: GunProviderService,
              private router: Router) { }

  ngOnInit(): void {
    this.gunProviderService.getGunProviders()
      .subscribe(gunProviders => this.gunProviders = gunProviders);
    this.gunProviderService.getGunProvidersSortedByName()
      .subscribe(gunProvidersSorted => this.gunProvidersSorted = gunProvidersSorted);
    this.sortClicked = false;
  }
  onSelect(gunProvider: GunProvider): void {
    this.selectedGunProvider = gunProvider;
  }

  goToDetail(): void {
    this.router.navigate(['/gun-provider/detail', this.selectedGunProvider.id]);
  }

  saveGunProvider(name: string, speciality: string, reputation: number) {
    const gunProvider: GunProvider = <GunProvider>{name: name, speciality: speciality, reputation: reputation};
    console.log(gunProvider);
    this.gunProviderService.saveGunProvider(gunProvider)
      // tslint:disable-next-line:no-shadowed-variable
      .subscribe();

  }

}
