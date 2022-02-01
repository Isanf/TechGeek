import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfessor } from 'app/shared/model/professor.model';
import { ProfessorService } from './professor.service';

@Component({
  templateUrl: './professor-delete-dialog.component.html'
})
export class ProfessorDeleteDialogComponent {
  professor?: IProfessor;

  constructor(protected professorService: ProfessorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.professorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('professorListModification');
      this.activeModal.close();
    });
  }
}
