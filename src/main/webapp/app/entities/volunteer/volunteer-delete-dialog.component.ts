import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVolunteer } from 'app/shared/model/volunteer.model';
import { VolunteerService } from './volunteer.service';

@Component({
  templateUrl: './volunteer-delete-dialog.component.html'
})
export class VolunteerDeleteDialogComponent {
  volunteer?: IVolunteer;

  constructor(protected volunteerService: VolunteerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.volunteerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('volunteerListModification');
      this.activeModal.close();
    });
  }
}
