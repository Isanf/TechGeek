import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IUploadfile } from 'app/shared/model/uploadfile.model';

@Component({
  selector: 'jhi-uploadfile-detail',
  templateUrl: './uploadfile-detail.component.html'
})
export class UploadfileDetailComponent implements OnInit {
  uploadfile: IUploadfile | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uploadfile }) => (this.uploadfile = uploadfile));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
