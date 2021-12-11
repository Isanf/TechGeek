import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILive } from 'app/shared/model/live.model';
import { LiveService } from './live.service';

@Component({
  templateUrl: './live-delete-dialog.component.html'
})
export class LiveDeleteDialogComponent {
  live?: ILive;

  constructor(protected liveService: LiveService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.liveService.delete(id).subscribe(() => {
      this.eventManager.broadcast('liveListModification');
      this.activeModal.close();
    });
  }
}
