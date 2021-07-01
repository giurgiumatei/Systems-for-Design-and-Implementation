import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {GunProvider} from './gun-provider.model';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GunProviderService {

  private gunProvidersUrl = 'http://localhost:8080/api/gun-providers';

  constructor(private httpClient: HttpClient) {
  }

  getGunProviders(): Observable<GunProvider[]> {
    return this.httpClient
      .get<Array<GunProvider>>(this.gunProvidersUrl);
  }

  getGunProvider(id: number): Observable<GunProvider> {
    return this.getGunProviders()
      .pipe(
        map(gunProviders => gunProviders.find(gunProvider => gunProvider.id === id))
      );
  }

  updateGunProvider(gunProvider: GunProvider): Observable<GunProvider> {
    const url = `${this.gunProvidersUrl}/${gunProvider.id}`;
    return this.httpClient
      .put<GunProvider>(url, gunProvider);
  }

  saveGunProvider(gunProvider: GunProvider): Observable<GunProvider> {
    return this.httpClient.post<GunProvider>(this.gunProvidersUrl, gunProvider);
  }

  deleteGunProvider(gunProvider: GunProvider): Observable<GunProvider> {
    const url = `${this.gunProvidersUrl}/${gunProvider.id}`;
    return this.httpClient
      .delete<GunProvider>(url);
  }

  getGunProvidersSortedByName(): Observable<GunProvider[]> {
    return this.httpClient
      .get<Array<GunProvider>>(this.gunProvidersUrl + '/sort/name');
  }
}
