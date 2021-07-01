import {Component, OnInit} from '@angular/core';
import {GunProviderService} from '../shared/gun-provider.service';
import {GunProvider} from '../shared/gun-provider.model';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-gun-provider-new',
  templateUrl: './gun-provider-new.component.html',
  styleUrls: ['./gun-provider-new.component.css']
})
export class GunProviderNewComponent implements OnInit {

  gunProviderForm: FormGroup;

  constructor(private gunProviderService: GunProviderService,
              private route: ActivatedRoute,
              private location: Location) {
  }


  get name() {
    return this.gunProviderForm.get('name');
  }

  get speciality() {
    return this.gunProviderForm.get('speciality');
  }

  get reputation() {
    return this.gunProviderForm.get('reputation');
  }

  ngOnInit(): void {
    this.gunProviderForm = new FormGroup({
      'name': new FormControl('', [
        Validators.required,
      ]),
      'speciality': new FormControl('', [
        Validators.required,
      ]),
      'reputation': new FormControl('', [
        Validators.required,
        Validators.min(1),
        Validators.max(10),
        Validators.pattern('^0$|^[1-9]+[0-9]*$'),
      ])
    });
  }

  goBack(): void {
    this.location.back();
  }

  saveGunProvider(name: string, speciality: string, reputation: string): void {
    this.gunProviderService.saveGunProvider(<GunProvider>{
      name,
      speciality,
      reputation: +reputation
    }).subscribe(_ => this.goBack());
  }

}
