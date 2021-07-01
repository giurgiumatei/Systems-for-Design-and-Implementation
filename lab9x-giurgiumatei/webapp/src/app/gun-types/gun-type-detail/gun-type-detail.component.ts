import {Component, Input, OnInit} from '@angular/core';
import {GunType} from '../shared/gun-type.model';
import {GunTypeService} from '../shared/gun-type.service';
import {ActivatedRoute, Params} from '@angular/router';
import {switchMap} from 'rxjs/operators';
import {Location} from '@angular/common';

@Component({
  selector: 'app-gun-type-detail',
  templateUrl: './gun-type-detail.component.html',
  styleUrls: ['./gun-type-detail.component.css']
})
export class GunTypeDetailComponent implements OnInit {

  @Input() gunType: GunType;

  constructor(private gunTypeService: GunTypeService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.gunTypeService.getGunType(+params['id'])))
      .subscribe(gunType => {
        this.gunType = gunType;
      });
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.gunTypeService.updateGunType(this.gunType)
      .subscribe(_ => this.goBack());
  }

  delete(): void {
    this.gunTypeService.deleteGunType(this.gunType)
      .subscribe(_ => this.goBack());
  }

}
