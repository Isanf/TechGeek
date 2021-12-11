import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupporter } from 'app/shared/model/supporter.model';
import { SupporterService } from './supporter.service';

@Component({
  templateUrl: './supporter-delete-dialog.component.html'
})
export class SupporterDeleteDialogComponent {
  supporter?: ISupporter;

  constructor(protected supporterService: SupporterService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.supporterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('supporterListModification');
      this.activeModal.close();
    });
  }
}
