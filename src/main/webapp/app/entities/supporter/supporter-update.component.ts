import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISupporter, Supporter } from 'app/shared/model/supporter.model';
import { SupporterService } from './supporter.service';

@Component({
  selector: 'jhi-supporter-update',
  templateUrl: './supporter-update.component.html'
})
export class SupporterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstname: [],
    lastname: [],
    address: [],
    email: [],
    phone: []
  });

  constructor(protected supporterService: SupporterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supporter }) => {
      this.updateForm(supporter);
    });
  }

  updateForm(supporter: ISupporter): void {
    this.editForm.patchValue({
      id: supporter.id,
      firstname: supporter.firstname,
      lastname: supporter.lastname,
      address: supporter.address,
      email: supporter.email,
      phone: supporter.phone
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const supporter = this.createFromForm();
    if (supporter.id !== undefined) {
      this.subscribeToSaveResponse(this.supporterService.update(supporter));
    } else {
      this.subscribeToSaveResponse(this.supporterService.create(supporter));
    }
  }

  private createFromForm(): ISupporter {
    return {
      ...new Supporter(),
      id: this.editForm.get(['id'])!.value,
      firstname: this.editForm.get(['firstname'])!.value,
      lastname: this.editForm.get(['lastname'])!.value,
      address: this.editForm.get(['address'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupporter>>): void {
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
