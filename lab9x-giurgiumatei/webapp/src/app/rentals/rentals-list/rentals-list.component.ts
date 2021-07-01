import { Component, OnInit } from '@angular/core';
import {GunType} from '../../gun-types/shared/gun-type.model';
import {Rental} from '../shared/rental.model';
import {RentalService} from '../shared/rental.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-rentals-list',
  templateUrl: './rentals-list.component.html',
  styleUrls: ['./rentals-list.component.css']
})
export class RentalsListComponent implements OnInit {

  rentals: Rental[];
  selectedRental: Rental;
  mostRentedGunType: GunType;

  constructor(public rentalService: RentalService,
              private router: Router) {
  }



  ngOnInit(): void {
    this.rentalService.getRentals()
      .subscribe(rentals => this.rentals = rentals);
    this.rentalService.getMostRentedGunType()
      .subscribe(gunType => this.mostRentedGunType = gunType);
  }

  onSelect(rental: Rental): void {
    this.selectedRental = rental;
    console.log(rental);
  }

}
