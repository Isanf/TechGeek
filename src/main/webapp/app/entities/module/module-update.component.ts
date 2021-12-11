import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IModule, Module } from 'app/shared/model/module.model';
import { ModuleService } from './module.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course/course.service';
import { IProfessor } from 'app/shared/model/professor.model';
import { ProfessorService } from 'app/entities/professor/professor.service';
import { IVolunteer } from 'app/shared/model/volunteer.model';
import { VolunteerService } from 'app/entities/volunteer/volunteer.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';

type SelectableEntity = ICategory | ICourse | IProfessor | IVolunteer | IStudent;

@Component({
  selector: 'jhi-module-update',
  templateUrl: './module-update.component.html'
})
export class ModuleUpdateComponent implements OnInit {
  isSaving = false;
  categories: ICategory[] = [];
  courses: ICourse[] = [];
  professors: IProfessor[] = [];
  volunteers: IVolunteer[] = [];
  students: IStudent[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    description: [],
    startDate: [],
    endDate: [],
    categoryId: [],
    courseId: [],
    professorId: [],
    volunteerId: [],
    studentId: []
  });

  constructor(
    protected moduleService: ModuleService,
    protected categoryService: CategoryService,
    protected courseService: CourseService,
    protected professorService: ProfessorService,
    protected volunteerService: VolunteerService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ module }) => {
      if (!module.id) {
        const today = moment().startOf('day');
        module.startDate = today;
        module.endDate = today;
      }

      this.updateForm(module);

      this.categoryService
        .query({ filter: 'module-is-null' })
        .pipe(
          map((res: HttpResponse<ICategory[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICategory[]) => {
          if (!module.categoryId) {
            this.categories = resBody;
          } else {
            this.categoryService
              .find(module.categoryId)
              .pipe(
                map((subRes: HttpResponse<ICategory>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICategory[]) => (this.categories = concatRes));
          }
        });

      this.courseService.query().subscribe((res: HttpResponse<ICourse[]>) => (this.courses = res.body || []));

      this.professorService.query().subscribe((res: HttpResponse<IProfessor[]>) => (this.professors = res.body || []));

      this.volunteerService.query().subscribe((res: HttpResponse<IVolunteer[]>) => (this.volunteers = res.body || []));

      this.studentService.query().subscribe((res: HttpResponse<IStudent[]>) => (this.students = res.body || []));
    });
  }

  updateForm(module: IModule): void {
    this.editForm.patchValue({
      id: module.id,
      title: module.title,
      description: module.description,
      startDate: module.startDate ? module.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: module.endDate ? module.endDate.format(DATE_TIME_FORMAT) : null,
      categoryId: module.categoryId,
      courseId: module.courseId,
      professorId: module.professorId,
      volunteerId: module.volunteerId,
      studentId: module.studentId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const module = this.createFromForm();
    if (module.id !== undefined) {
      this.subscribeToSaveResponse(this.moduleService.update(module));
    } else {
      this.subscribeToSaveResponse(this.moduleService.create(module));
    }
  }

  private createFromForm(): IModule {
    return {
      ...new Module(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      categoryId: this.editForm.get(['categoryId'])!.value,
      courseId: this.editForm.get(['courseId'])!.value,
      professorId: this.editForm.get(['professorId'])!.value,
      volunteerId: this.editForm.get(['volunteerId'])!.value,
      studentId: this.editForm.get(['studentId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModule>>): void {
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
