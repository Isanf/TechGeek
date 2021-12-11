import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILevel } from 'app/shared/model/level.model';
import { LevelService } from './level.service';

@Component({
  templateUrl: './level-delete-dialog.component.html'
})
export class LevelDeleteDialogComponent {
  level?: ILevel;

  constructor(protected levelService: LevelService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.levelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('levelListModification');
      this.activeModal.close();
    });
  }
}
