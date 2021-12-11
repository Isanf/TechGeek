import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFramework } from 'app/shared/model/framework.model';

@Component({
  selector: 'jhi-framework-detail',
  templateUrl: './framework-detail.component.html'
})
export class FrameworkDetailComponent implements OnInit {
  framework: IFramework | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ framework }) => (this.framework = framework));
  }

  previousState(): void {
    window.history.back();
  }
}
