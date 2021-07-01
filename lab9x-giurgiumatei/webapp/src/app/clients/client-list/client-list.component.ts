import { Component, OnInit } from '@angular/core';
import { Client} from '../shared/client.model';
import {ClientService} from '../shared/client.service';
import {Router, Routes} from '@angular/router';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  clients: Client[];
  selectedClient: Client;
  newClicked: boolean;
  date: string;

  constructor(private clientService: ClientService,
              private router: Router) { }

  ngOnInit(): void {
    this.clientService.getClients()
      .subscribe(clients => this.clients = clients);
    this.newClicked = false;
    this.date = '';
  }
  onSelect(client: Client): void {
    this.selectedClient = client;
  }

  goToDetail(): void {
    this.router.navigate(['/client/detail', this.selectedClient.id]);
}

  saveClient(name: string, date: string, email: string, username: string, password: string) {
    this.newClicked = false;
    const dateOfBirth = date.split('-').map(x => +x);
    console.log(name, dateOfBirth);
    const client: Client = <Client>{name: name, dateOfBirth: dateOfBirth, account: {username: username, password: password, email: email}};
    this.clientService.saveClient(client)
      // tslint:disable-next-line:no-shadowed-variable
      .subscribe();

  }

  search(): any {
    if (this.date === '') {
      this.ngOnInit();
    } else {
      this.clientService.getAllClientsBornBefore(this.date).subscribe(clients => this.clients = clients);
    }
  }

}
