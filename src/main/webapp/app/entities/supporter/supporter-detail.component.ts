import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupporter } from 'app/shared/model/supporter.model';

@Component({
  selector: 'jhi-supporter-detail',
  templateUrl: './supporter-detail.component.html'
})
export class SupporterDetailComponent implements OnInit {
  supporter: ISupporter | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supporter }) => (this.supporter = supporter));
  }

  previousState(): void {
    window.history.back();
  }
}
