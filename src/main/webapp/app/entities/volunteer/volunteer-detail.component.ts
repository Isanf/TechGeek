import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVolunteer } from 'app/shared/model/volunteer.model';

@Component({
  selector: 'jhi-volunteer-detail',
  templateUrl: './volunteer-detail.component.html'
})
export class VolunteerDetailComponent implements OnInit {
  volunteer: IVolunteer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ volunteer }) => (this.volunteer = volunteer));
  }

  previousState(): void {
    window.history.back();
  }
}
