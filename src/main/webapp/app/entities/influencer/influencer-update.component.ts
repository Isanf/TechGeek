import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInfluencer, Influencer } from 'app/shared/model/influencer.model';
import { InfluencerService } from './influencer.service';

@Component({
  selector: 'jhi-influencer-update',
  templateUrl: './influencer-update.component.html'
})
export class InfluencerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstname: [],
    lastname: [],
    address: [],
    email: [],
    phone: []
  });

  constructor(protected influencerService: InfluencerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ influencer }) => {
      this.updateForm(influencer);
    });
  }

  updateForm(influencer: IInfluencer): void {
    this.editForm.patchValue({
      id: influencer.id,
      firstname: influencer.firstname,
      lastname: influencer.lastname,
      address: influencer.address,
      email: influencer.email,
      phone: influencer.phone
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const influencer = this.createFromForm();
    if (influencer.id !== undefined) {
      this.subscribeToSaveResponse(this.influencerService.update(influencer));
    } else {
      this.subscribeToSaveResponse(this.influencerService.create(influencer));
    }
  }

  private createFromForm(): IInfluencer {
    return {
      ...new Influencer(),
      id: this.editForm.get(['id'])!.value,
      firstname: this.editForm.get(['firstname'])!.value,
      lastname: this.editForm.get(['lastname'])!.value,
      address: this.editForm.get(['address'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInfluencer>>): void {
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
