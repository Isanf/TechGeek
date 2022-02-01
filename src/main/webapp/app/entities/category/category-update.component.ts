import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICategory, Category } from 'app/shared/model/category.model';
import { CategoryService } from './category.service';
import { ISupporter } from 'app/shared/model/supporter.model';
import { SupporterService } from 'app/entities/supporter/supporter.service';
import { IInfluencer } from 'app/shared/model/influencer.model';
import { InfluencerService } from 'app/entities/influencer/influencer.service';

type SelectableEntity = ISupporter | IInfluencer;

@Component({
  selector: 'jhi-category-update',
  templateUrl: './category-update.component.html'
})
export class CategoryUpdateComponent implements OnInit {
  isSaving = false;
  supporters: ISupporter[] = [];
  influencers: IInfluencer[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    supporterId: [],
    influencerId: []
  });

  constructor(
    protected categoryService: CategoryService,
    protected supporterService: SupporterService,
    protected influencerService: InfluencerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ category }) => {
      this.updateForm(category);

      this.supporterService.query().subscribe((res: HttpResponse<ISupporter[]>) => (this.supporters = res.body || []));

      this.influencerService.query().subscribe((res: HttpResponse<IInfluencer[]>) => (this.influencers = res.body || []));
    });
  }

  updateForm(category: ICategory): void {
    this.editForm.patchValue({
      id: category.id,
      title: category.title,
      description: category.description,
      supporterId: category.supporterId,
      influencerId: category.influencerId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const category = this.createFromForm();
    if (category.id !== undefined) {
      this.subscribeToSaveResponse(this.categoryService.update(category));
    } else {
      this.subscribeToSaveResponse(this.categoryService.create(category));
    }
  }

  private createFromForm(): ICategory {
    return {
      ...new Category(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      supporterId: this.editForm.get(['supporterId'])!.value,
      influencerId: this.editForm.get(['influencerId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategory>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
