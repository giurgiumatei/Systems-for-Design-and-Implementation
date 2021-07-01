import {Component, Input, OnInit} from '@angular/core';
import {ClientService} from '../shared/client.service';
import {ActivatedRoute, Params} from '@angular/router';
import {Location} from '@angular/common';
import {Client} from '../shared/client.model';
import {switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css']
})
export class ClientDetailComponent implements OnInit {

  @Input() client: Client;
  date: string;

  constructor(private clientService: ClientService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.clientService.getClient(+params['id'])))
      .subscribe(client => {
        this.client = client;
        this.date = client.dateOfBirth.join('-');
      });
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.client.dateOfBirth = this.date.split('-').map(x => +x);
    this.clientService.updateClient(this.client)
      .subscribe(_ => this.goBack());
  }

  delete(): void {
    this.clientService.deleteClient(this.client)
      .subscribe(_ => this.goBack());
  }
}
