import { Component, OnInit } from '@angular/core';
import { ApiService } from './../../services/api.service';

@Component({
  selector: 'app-supervisor',
  templateUrl: './supervisor.component.html',
  styleUrls: ['./supervisor.component.scss']
})
export class SupervisorComponent implements OnInit {

  colaboradorList: any[] = [];
  subordinado!: number;
  chefe!: number;

  constructor(private ApiService: ApiService) { }

  ngOnInit(): void {
    this.getUserList();
  }

  getUserList(){
    this.ApiService.getUserList().subscribe((data) => {
      console.log(data);
      this.colaboradorList = data;
    });
  }

  onSubmit(){

    if (this.chefe != null && this.subordinado != null) {
      this.ApiService.associateBoss(this.chefe, this.subordinado).subscribe((data) => {
        console.log(data);
      });
    } else alert('Preencha os campos corretamente!');
  }

}
