import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastroComponent } from './pages/cadastro/cadastro.component';
import { ListComponent } from './pages/listar/listar.component';
import { SupervisorComponent } from './pages/supervisor/supervisor.component';

const routes: Routes = [
  { path: '', redirectTo: '/cadastro', pathMatch: 'full' }, // Rota padrão
  { path: 'cadastro', component: CadastroComponent },
  { path: 'listar', component: ListComponent },
  { path: 'supervisor', component: SupervisorComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
