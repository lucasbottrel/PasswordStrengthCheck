import { ApiService } from './../../services/api.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss']
})
export class CadastroComponent implements OnInit {

  constructor(private ApiService: ApiService) {

  }

  user = {
    name: '',
    password: ''
  };

  ngOnInit(): void {
  }

  onSubmit(): void {
    console.warn(this.user);
    this.ApiService.addCadastro(this.user.name, this.user.password).subscribe((data) => {
      console.log(data);
    });
  }

}
