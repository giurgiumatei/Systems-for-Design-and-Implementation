import {Component, OnInit} from '@angular/core';
import {GunTypeService} from '../shared/gun-type.service';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {GunType} from '../shared/gun-type.model';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-gun-type-new',
  templateUrl: './gun-type-new.component.html',
  styleUrls: ['./gun-type-new.component.css']
})

export class GunTypeNewComponent implements OnInit {

  categories = ['PISTOL',
    'RIFLE',
    'SHOTGUN',
    'LONG_GUN',
    'SNIPER'];
  gunProviders: string[];

  constructor(private gunTypeService: GunTypeService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.gunTypeService.getGunProviders()
      .subscribe(gunProviders => this.gunProviders = gunProviders.map(gunProvider => gunProvider.name));
  }

  goBack(): void {
    this.location.back();
  }

  saveGunType(gunProviderForm: NgForm) {
    const gunType: GunType = <GunType>{
      name: gunProviderForm.value.name,
      category: gunProviderForm.value.category,
      gunProviderName: gunProviderForm.value.gunProviderName
    };
    console.log(gunType);
    this.gunTypeService.saveGunType(gunType)
      // tslint:disable-next-line:no-shadowed-variable
      .subscribe(_ => this.goBack());

  }

  onSubmit() {

  }
}
