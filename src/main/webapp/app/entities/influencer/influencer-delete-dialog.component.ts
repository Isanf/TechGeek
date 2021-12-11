import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInfluencer } from 'app/shared/model/influencer.model';
import { InfluencerService } from './influencer.service';

@Component({
  templateUrl: './influencer-delete-dialog.component.html'
})
export class InfluencerDeleteDialogComponent {
  influencer?: IInfluencer;

  constructor(
    protected influencerService: InfluencerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.influencerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('influencerListModification');
      this.activeModal.close();
    });
  }
}
