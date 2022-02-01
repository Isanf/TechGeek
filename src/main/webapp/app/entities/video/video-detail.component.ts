import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVideo } from 'app/shared/model/video.model';

@Component({
  selector: 'jhi-video-detail',
  templateUrl: './video-detail.component.html'
})
export class VideoDetailComponent implements OnInit {
  video: IVideo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ video }) => (this.video = video));
  }

  previousState(): void {
    window.history.back();
  }
}
