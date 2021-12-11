import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVideo } from 'app/shared/model/video.model';
import { VideoService } from './video.service';

@Component({
  templateUrl: './video-delete-dialog.component.html'
})
export class VideoDeleteDialogComponent {
  video?: IVideo;

  constructor(protected videoService: VideoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.videoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('videoListModification');
      this.activeModal.close();
    });
  }
}
