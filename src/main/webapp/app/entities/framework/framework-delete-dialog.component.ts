import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFramework } from 'app/shared/model/framework.model';
import { FrameworkService } from './framework.service';

@Component({
  templateUrl: './framework-delete-dialog.component.html'
})
export class FrameworkDeleteDialogComponent {
  framework?: IFramework;

  constructor(protected frameworkService: FrameworkService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.frameworkService.delete(id).subscribe(() => {
      this.eventManager.broadcast('frameworkListModification');
      this.activeModal.close();
    });
  }
}
