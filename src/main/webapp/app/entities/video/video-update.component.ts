import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVideo, Video } from 'app/shared/model/video.model';
import { VideoService } from './video.service';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module/module.service';

@Component({
  selector: 'jhi-video-update',
  templateUrl: './video-update.component.html'
})
export class VideoUpdateComponent implements OnInit {
  isSaving = false;
  modules: IModule[] = [];

  fileInfos: File | undefined;

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
    protected videoService: VideoService,
    protected moduleService: ModuleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ video }) => {
      if (!video.id) {
        const today = moment().startOf('day');
        video.startDate = today;
        video.endDate = today;
      }

      this.updateForm(video);

      this.moduleService
        .query({ filter: 'video-is-null' })
        .pipe(
          map((res: HttpResponse<IModule[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IModule[]) => {
          if (!video.moduleId) {
            this.modules = resBody;
          } else {
            this.moduleService
              .find(video.moduleId)
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

  updateForm(video: IVideo): void {
    this.editForm.patchValue({
      id: video.id,
      name: video.name,
      description: video.description,
      source: video.source,
      startDate: video.startDate ? video.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: video.endDate ? video.endDate.format(DATE_TIME_FORMAT) : null,
      premium: video.premium,
      moduleId: video.moduleId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const video = this.createFromForm();
    if (video.id !== undefined) {
      this.subscribeToSaveResponse(this.videoService.update(video));
    } else {
      this.subscribeToSaveResponse(this.videoService.create(video));
    }
  }

  private createFromForm(): IVideo {
    return {
      ...new Video(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideo>>): void {
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
