import {Component, OnInit} from '@angular/core';
import {GunType} from '../shared/gun-type.model';
import {GunTypeService} from '../shared/gun-type.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-gun-type-list',
  templateUrl: './gun-type-list.component.html',
  styleUrls: ['./gun-type-list.component.css']
})
export class GunTypeListComponent implements OnInit {

  gunTypes: GunType[];
  selectedGunType: GunType;
  category: any;


  constructor(public gunTypeService: GunTypeService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.gunTypeService.getGunTypes()
      .subscribe(gunTypes => this.gunTypes = gunTypes);
  }

  onSelect(gunType: GunType): void {
    this.selectedGunType = gunType;
  }

  goToDetail(): void {
    this.router.navigate(['/gun-type/detail', this.selectedGunType.id]);
  }

  saveGunType(name: string, category: string, gunProviderName: string) {
    const gunType: GunType = <GunType>{name: name, category: category, gunProviderName: gunProviderName};
    console.log(gunType);
    this.gunTypeService.saveGunType(gunType)
      // tslint:disable-next-line:no-shadowed-variable
      .subscribe();

  }

  search(): any {
    if (this.category === '') {
      this.ngOnInit();
    } else {
      this.gunTypes = this.gunTypes.filter(res => {
        return res.category.toLocaleLowerCase().match(this.category.toLocaleLowerCase());
      });
    }
  }

  sortByName(): void {
    this.gunTypes.sort((gunTypeFirst: GunType, gunTypeSecond: GunType) =>
      (gunTypeFirst.name.toLowerCase() > gunTypeSecond.name.toLowerCase() ? 1 : -1));
  }
}
