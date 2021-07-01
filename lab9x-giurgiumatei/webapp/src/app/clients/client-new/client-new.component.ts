import {Component, OnInit} from '@angular/core';
import {ClientService} from '../shared/client.service';
import {Client} from '../shared/client.model';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-client-new',
  templateUrl: './client-new.component.html',
  styleUrls: ['./client-new.component.css']
})
export class ClientNewComponent implements OnInit {

  constructor(private clientService: ClientService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back();
  }

  saveClient(name: string, date: string) {
    const dateOfBirth = date.split('-').map(x => +x);
    console.log(name, dateOfBirth);
    const client: Client = <Client>{name: name, dateOfBirth: dateOfBirth};
    this.clientService.saveClient(client)
      // tslint:disable-next-line:no-shadowed-variable
      .subscribe(_ => this.goBack());

  }
}
