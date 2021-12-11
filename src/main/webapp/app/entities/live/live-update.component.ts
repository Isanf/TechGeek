import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILive, Live } from 'app/shared/model/live.model';
import { LiveService } from './live.service';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module/module.service';

@Component({
  selector: 'jhi-live-update',
  templateUrl: './live-update.component.html'
})
export class LiveUpdateComponent implements OnInit {
  isSaving = false;
  modules: IModule[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    source: [],
    startDate: [],
    endDate: [],
    premium: [],
    moduleId: []
  });

  constructor(
    protected liveService: LiveService,
    protected moduleService: ModuleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ live }) => {
      if (!live.id) {
        const today = moment().startOf('day');
        live.startDate = today;
        live.endDate = today;
      }

      this.updateForm(live);

      this.moduleService
        .query({ filter: 'live-is-null' })
        .pipe(
          map((res: HttpResponse<IModule[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IModule[]) => {
          if (!live.moduleId) {
            this.modules = resBody;
          } else {
            this.moduleService
              .find(live.moduleId)
              .pipe(
                map((subRes: HttpResponse<IModule>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IModule[]) => (this.modules = concatRes));
          }
        });
    });
  }

  updateForm(live: ILive): void {
    this.editForm.patchValue({
      id: live.id,
      name: live.name,
      description: live.description,
      source: live.source,
      startDate: live.startDate ? live.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: live.endDate ? live.endDate.format(DATE_TIME_FORMAT) : null,
      premium: live.premium,
      moduleId: live.moduleId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const live = this.createFromForm();
    if (live.id !== undefined) {
      this.subscribeToSaveResponse(this.liveService.update(live));
    } else {
      this.subscribeToSaveResponse(this.liveService.create(live));
    }
  }

  private createFromForm(): ILive {
    return {
      ...new Live(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      source: this.editForm.get(['source'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      premium: this.editForm.get(['premium'])!.value,
      moduleId: this.editForm.get(['moduleId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILive>>): void {
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

  trackById(index: number, item: IModule): any {
    return item.id;
  }
}
