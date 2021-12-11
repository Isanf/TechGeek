import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILive } from 'app/shared/model/live.model';

@Component({
  selector: 'jhi-live-detail',
  templateUrl: './live-detail.component.html'
})
export class LiveDetailComponent implements OnInit {
  live: ILive | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ live }) => (this.live = live));
  }

  previousState(): void {
    window.history.back();
  }
}
