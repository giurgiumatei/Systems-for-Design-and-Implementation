import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {GunType} from './gun-type.model';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {GunProvider} from '../../gun-providers/shared/gun-provider.model';

@Injectable({
  providedIn: 'root'
})
export class GunTypeService {

  private gunTypesUrl = 'http://localhost:8080/api/gun-types';
  private gunProvidersUrl = 'http://localhost:8080/api/gun-providers';

  constructor(private httpClient: HttpClient) {
  }

  getGunTypes(): Observable<GunType[]> {
    return this.httpClient
      .get<Array<GunType>>(this.gunTypesUrl);
  }
  getGunProviders(): Observable<GunProvider[]> {
    return this.httpClient
      .get<Array<GunProvider>>(this.gunProvidersUrl);
  }

  getGunType(id: number): Observable<GunType> {
    return this.getGunTypes()
      .pipe(
        map(gunTypes => gunTypes.find(gunType => gunType.id === id))
      );
  }

  updateGunType(gunType: GunType): Observable<GunType> {
    const url = `${this.gunTypesUrl}/${gunType.id}`;
    return this.httpClient
      .put<GunType>(url, gunType);
  }

  saveGunType(gunType: GunType): Observable<GunType> {
    return this.httpClient.post<GunType>(this.gunTypesUrl, gunType);
  }

  deleteGunType(gunType: GunType): Observable<GunType> {
    const url = `${this.gunTypesUrl}/${gunType.id}`;
    return this.httpClient
      .delete<GunType>(url);
  }

  getGunProviderById(id: number): Observable<GunProvider> {
    const url = `${this.gunProvidersUrl}/${id}`;
    return this.httpClient
      .get<GunProvider>(url);
  }
}
