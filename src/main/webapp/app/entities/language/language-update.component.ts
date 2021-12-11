import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILanguage, Language } from 'app/shared/model/language.model';
import { LanguageService } from './language.service';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module/module.service';

@Component({
  selector: 'jhi-language-update',
  templateUrl: './language-update.component.html'
})
export class LanguageUpdateComponent implements OnInit {
  isSaving = false;
  modules: IModule[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    moduleId: []
  });

  constructor(
    protected languageService: LanguageService,
    protected moduleService: ModuleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ language }) => {
      this.updateForm(language);

      this.moduleService.query().subscribe((res: HttpResponse<IModule[]>) => (this.modules = res.body || []));
    });
  }

  updateForm(language: ILanguage): void {
    this.editForm.patchValue({
      id: language.id,
      title: language.title,
      description: language.description,
      moduleId: language.moduleId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const language = this.createFromForm();
    if (language.id !== undefined) {
      this.subscribeToSaveResponse(this.languageService.update(language));
    } else {
      this.subscribeToSaveResponse(this.languageService.create(language));
    }
  }

  private createFromForm(): ILanguage {
    return {
      ...new Language(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      moduleId: this.editForm.get(['moduleId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILanguage>>): void {
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
