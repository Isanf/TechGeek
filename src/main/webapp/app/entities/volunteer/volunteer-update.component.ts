import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVolunteer, Volunteer } from 'app/shared/model/volunteer.model';
import { VolunteerService } from './volunteer.service';

@Component({
  selector: 'jhi-volunteer-update',
  templateUrl: './volunteer-update.component.html'
})
export class VolunteerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstname: [],
    lastname: [],
    address: [],
    email: [],
    phone: []
  });

  constructor(protected volunteerService: VolunteerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ volunteer }) => {
      this.updateForm(volunteer);
    });
  }

  updateForm(volunteer: IVolunteer): void {
    this.editForm.patchValue({
      id: volunteer.id,
      firstname: volunteer.firstname,
      lastname: volunteer.lastname,
      address: volunteer.address,
      email: volunteer.email,
      phone: volunteer.phone
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const volunteer = this.createFromForm();
    if (volunteer.id !== undefined) {
      this.subscribeToSaveResponse(this.volunteerService.update(volunteer));
    } else {
      this.subscribeToSaveResponse(this.volunteerService.create(volunteer));
    }
  }

  private createFromForm(): IVolunteer {
    return {
      ...new Volunteer(),
      id: this.editForm.get(['id'])!.value,
      firstname: this.editForm.get(['firstname'])!.value,
      lastname: this.editForm.get(['lastname'])!.value,
      address: this.editForm.get(['address'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVolunteer>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
