import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ApiService} from "../../service/api.service";
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-person',
  templateUrl: './add-person.component.html',
  styleUrls: ['./add-person.component.css']
})
export class AddPersonComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,private router: Router, private apiService: ApiService, private toastr: ToastrService) { }

  addForm: FormGroup;
  submitted = false;

  ngOnInit() {
    this.addForm = this.formBuilder.group({
      cpf: ['', [Validators.required]],
      dateBirth: ['', Validators.required],
      gender: [''],
      mail: ['', Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")],
      name: ['', Validators.required],
      nationality: [''],
      placeBirth: ['']
    });

  }

  get f() { 
    return this.addForm.controls; 
  }

  onSubmit() {
    this.submitted = true;
    if (this.addForm.invalid) {
      return;
    }
    this.apiService.createPerson(this.addForm.value)
      .subscribe( () => {
        this.router.navigate(['list-person']);
      },
      error => {
        this.toastr.error('Verifique informações de pessoa', 'Problemas ao adicionar');
      });
  }

}
