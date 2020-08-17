import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {AddPersonComponent} from "./person/add-person/add-person.component";
import {ListPersonComponent} from "./person/list-person/list-person.component";
import {EditPersonComponent} from "./person/edit-person/edit-person.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'add-person', component: AddPersonComponent },
  { path: 'list-person', component: ListPersonComponent },
  { path: 'edit-person', component: EditPersonComponent },
  {path : '', component : LoginComponent}
];

export const routing = RouterModule.forRoot(routes);
