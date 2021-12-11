import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILevel, Level } from 'app/shared/model/level.model';
import { LevelService } from './level.service';

@Component({
  selector: 'jhi-level-update',
  templateUrl: './level-update.component.html'
})
export class LevelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [],
    description: []
  });

  constructor(protected levelService: LevelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ level }) => {
      this.updateForm(level);
    });
  }

  updateForm(level: ILevel): void {
    this.editForm.patchValue({
      id: level.id,
      title: level.title,
      description: level.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const level = this.createFromForm();
    if (level.id !== undefined) {
      this.subscribeToSaveResponse(this.levelService.update(level));
    } else {
      this.subscribeToSaveResponse(this.levelService.create(level));
    }
  }

  private createFromForm(): ILevel {
    return {
      ...new Level(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILevel>>): void {
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
