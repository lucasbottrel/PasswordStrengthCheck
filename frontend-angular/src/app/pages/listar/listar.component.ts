import { Component, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { ApiService } from './../../services/api.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-list',
  templateUrl: './listar.component.html',
  styleUrls: ['./listar.component.scss']
})
export class ListComponent implements OnInit{

  constructor(private ApiService: ApiService) { }

  colaboradorList: any[] = [];
  displayedColumns: string[] = ['id', 'nome', 'score', 'chefe'];
  dataSource = new MatTableDataSource<any>(this.colaboradorList);

  ngOnInit(): void {
    this.getUserList();
  }

  getUserList(){
    this.ApiService.getUserList().subscribe((data) => {
      console.log(data);
      this.colaboradorList = data;

      // Após obter os dados, atualize a fonte de dados
      this.dataSource = new MatTableDataSource<any>(this.colaboradorList);
      this.dataSource.paginator = this.paginator;
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    //Called before any other lifecycle hook. Use it to inject dependencies, but avoid any serious work here.
    //Add '${implements OnChanges}' to the class.
    this.getUserList();

  }

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

}

