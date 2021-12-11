import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFramework, Framework } from 'app/shared/model/framework.model';
import { FrameworkService } from './framework.service';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module/module.service';

@Component({
  selector: 'jhi-framework-update',
  templateUrl: './framework-update.component.html'
})
export class FrameworkUpdateComponent implements OnInit {
  isSaving = false;
  modules: IModule[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    moduleId: []
  });

  constructor(
    protected frameworkService: FrameworkService,
    protected moduleService: ModuleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ framework }) => {
      this.updateForm(framework);

      this.moduleService.query().subscribe((res: HttpResponse<IModule[]>) => (this.modules = res.body || []));
    });
  }

  updateForm(framework: IFramework): void {
    this.editForm.patchValue({
      id: framework.id,
      title: framework.title,
      description: framework.description,
      moduleId: framework.moduleId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const framework = this.createFromForm();
    if (framework.id !== undefined) {
      this.subscribeToSaveResponse(this.frameworkService.update(framework));
    } else {
      this.subscribeToSaveResponse(this.frameworkService.create(framework));
    }
  }

  private createFromForm(): IFramework {
    return {
      ...new Framework(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      moduleId: this.editForm.get(['moduleId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFramework>>): void {
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
