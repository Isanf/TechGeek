import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';
import { ILevel } from 'app/shared/model/level.model';
import { LevelService } from 'app/entities/level/level.service';

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html'
})
export class StudentUpdateComponent implements OnInit {
  isSaving = false;
  levels: ILevel[] = [];

  editForm = this.fb.group({
    id: [],
    firstname: [],
    lastname: [],
    address: [],
    email: [],
    phone: [],
    levelId: []
  });

  constructor(
    protected studentService: StudentService,
    protected levelService: LevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ student }) => {
      this.updateForm(student);

      this.levelService
        .query({ filter: 'student-is-null' })
        .pipe(
          map((res: HttpResponse<ILevel[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ILevel[]) => {
          if (!student.levelId) {
            this.levels = resBody;
          } else {
            this.levelService
              .find(student.levelId)
              .pipe(
                map((subRes: HttpResponse<ILevel>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILevel[]) => (this.levels = concatRes));
          }
        });
    });
  }

  updateForm(student: IStudent): void {
    this.editForm.patchValue({
      id: student.id,
      firstname: student.firstname,
      lastname: student.lastname,
      address: student.address,
      email: student.email,
      phone: student.phone,
      levelId: student.levelId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id'])!.value,
      firstname: this.editForm.get(['firstname'])!.value,
      lastname: this.editForm.get(['lastname'])!.value,
      address: this.editForm.get(['address'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      levelId: this.editForm.get(['levelId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>): void {
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

  trackById(index: number, item: ILevel): any {
    return item.id;
  }
}
