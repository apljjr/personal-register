import { Component, OnInit , Inject} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Person} from "../../model/person.model";
import {ApiService} from "../../service/api.service";

@Component({
  selector: 'app-edit-person',
  templateUrl: './edit-person.component.html',
  styleUrls: ['./edit-person.component.css']
})
export class EditPersonComponent implements OnInit {

  editForm: FormGroup;
  submitted = false;

  constructor(private formBuilder: FormBuilder,private router: Router, private apiService: ApiService) { }

  ngOnInit() {
    let personCpf = window.localStorage.getItem("editPersonCpf");
    if(!personCpf) {
      alert("Invalid action.")
      this.router.navigate(['list-person']);
      return;
    }
     this.editForm = this.formBuilder.group({
        cpf: ['', [Validators.required]],
        dateBirth: ['', Validators.required],
        gender: [''],
        mail: ['', Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")],
        name: ['', Validators.required],
        nationality: [''],
        placeBirth: ['']
      });
    
    this.apiService.getPersonById(personCpf)
      .subscribe( data => {
        this.editForm.setValue(data);
      });
  }

  get f() { 
    return this.editForm.controls; 
  }

  onSubmit() {
    this.submitted = true;
    if (this.editForm.invalid) {
      return;
    }
    this.apiService.updatePerson(this.editForm.value)
      .subscribe(() => {
          debugger;
            this.router.navigate(['list-person']);
        },
        error => {
          alert(error);
        });
  }

}
