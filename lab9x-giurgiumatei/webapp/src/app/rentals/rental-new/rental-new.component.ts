import { Component, OnInit } from '@angular/core';
import {GunTypeService} from '../../gun-types/shared/gun-type.service';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {RentalService} from '../shared/rental.service';
import {ClientService} from '../../clients/shared/client.service';
import {NgForm} from '@angular/forms';
import {GunType} from '../../gun-types/shared/gun-type.model';
import {Rental} from '../shared/rental.model';

@Component({
  selector: 'app-rental-new',
  templateUrl: './rental-new.component.html',
  styleUrls: ['./rental-new.component.css']
})
export class RentalNewComponent implements OnInit {
  clients: string[];
  gunTypes: string[];

  constructor(private rentalService: RentalService,
              private clientService: ClientService,
              private gunTypeService: GunTypeService,
              private route: ActivatedRoute,
              private location: Location) { }

  ngOnInit(): void {
    this.gunTypeService.getGunTypes()
      .subscribe(gunTypes => this.gunTypes = gunTypes.map(gunType => gunType.name));
    this.clientService.getClients()
      .subscribe(clients => this.clients = clients.map(client => client.name));
  }

  goBack(): void {
    this.location.back();
  }

  saveRental(rentalForm: NgForm) {
    const rental: Rental = <Rental>{
      clientName: rentalForm.value.clientName,
      gunTypeName: rentalForm.value.gunTypeName,
      price: rentalForm.value.price
    };
    console.log(rental);
    this.rentalService.saveRental(rental)
      // tslint:disable-next-line:no-shadowed-variable
      .subscribe(_ => this.goBack());

  }

}
