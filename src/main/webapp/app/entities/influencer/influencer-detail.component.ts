import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInfluencer } from 'app/shared/model/influencer.model';

@Component({
  selector: 'jhi-influencer-detail',
  templateUrl: './influencer-detail.component.html'
})
export class InfluencerDetailComponent implements OnInit {
  influencer: IInfluencer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ influencer }) => (this.influencer = influencer));
  }

  previousState(): void {
    window.history.back();
  }
}
