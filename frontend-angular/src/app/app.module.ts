import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CadastroComponent } from './pages/cadastro/cadastro.component';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { ListComponent } from './pages/listar/listar.component';
import { SupervisorComponent } from './pages/supervisor/supervisor.component';
import { MatTableModule } from '@angular/material/table';
import {MatDividerModule} from '@angular/material/divider';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSelectModule} from '@angular/material/select';

@NgModule({
  declarations: [
    AppComponent,
    CadastroComponent,
    ListComponent,
    SupervisorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatTableModule,
    MatDividerModule,
    MatPaginatorModule,
    MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
