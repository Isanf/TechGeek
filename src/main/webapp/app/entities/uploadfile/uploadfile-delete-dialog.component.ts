import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUploadfile } from 'app/shared/model/uploadfile.model';
import { UploadfileService } from './uploadfile.service';

@Component({
  templateUrl: './uploadfile-delete-dialog.component.html'
})
export class UploadfileDeleteDialogComponent {
  uploadfile?: IUploadfile;

  constructor(
    protected uploadfileService: UploadfileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.uploadfileService.delete(id).subscribe(() => {
      this.eventManager.broadcast('uploadfileListModification');
      this.activeModal.close();
    });
  }
}
