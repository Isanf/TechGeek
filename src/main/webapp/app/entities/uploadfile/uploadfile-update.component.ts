import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IUploadfile, Uploadfile } from 'app/shared/model/uploadfile.model';
import { UploadfileService } from './uploadfile.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-uploadfile-update',
  templateUrl: './uploadfile-update.component.html'
})
export class UploadfileUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    myfile: [],
    myfileContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected uploadfileService: UploadfileService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uploadfile }) => {
      this.updateForm(uploadfile);
    });
  }

  updateForm(uploadfile: IUploadfile): void {
    this.editForm.patchValue({
      id: uploadfile.id,
      myfile: uploadfile.myfile,
      myfileContentType: uploadfile.myfileContentType
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('techGeekApp.error', { message: err.message })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const uploadfile = this.createFromForm();
    if (uploadfile.id !== undefined) {
      this.subscribeToSaveResponse(this.uploadfileService.update(uploadfile));
    } else {
      this.subscribeToSaveResponse(this.uploadfileService.create(uploadfile));
    }
  }

  private createFromForm(): IUploadfile {
    return {
      ...new Uploadfile(),
      id: this.editForm.get(['id'])!.value,
      myfileContentType: this.editForm.get(['myfileContentType'])!.value,
      myfile: this.editForm.get(['myfile'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUploadfile>>): void {
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
