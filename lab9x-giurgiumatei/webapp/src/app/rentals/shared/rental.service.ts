import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GunType} from '../../gun-types/shared/gun-type.model';
import {Rental} from './rental.model';
import {Client} from '../../clients/shared/client.model';

@Injectable({
  providedIn: 'root'
})
export class RentalService {

  private rentalsUrl = 'http://localhost:8080/api/rentals';
  private gunTypesUrl = 'http://localhost:8080/api/gun-types';
  private clientsUrl = 'http://localhost:8080/api/clients';
  private mostRentedUrl = 'http://localhost:8080/api/most-rented';

  constructor(private httpClient: HttpClient) {
  }

  getRentals(): Observable<Rental[]> {
    return this.httpClient
      .get<Array<Rental>>(this.rentalsUrl);
  }

  getGunTypes(): Observable<GunType[]> {
    return this.httpClient
      .get<Array<GunType>>(this.gunTypesUrl);
  }

  getClients(): Observable<Client[]> {
    return this.httpClient
      .get<Array<Client>>(this.clientsUrl);
  }

  saveRental(rental: Rental): Observable<Rental> {
    console.log(rental);
    return this.httpClient.post<Rental>(this.rentalsUrl, rental);
  }

  getMostRentedGunType(): Observable<GunType> {
    return this.httpClient
      .get<GunType>(this.mostRentedUrl);
  }
}
