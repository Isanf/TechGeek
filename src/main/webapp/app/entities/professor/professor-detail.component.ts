import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfessor } from 'app/shared/model/professor.model';

@Component({
  selector: 'jhi-professor-detail',
  templateUrl: './professor-detail.component.html'
})
export class ProfessorDetailComponent implements OnInit {
  professor: IProfessor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ professor }) => (this.professor = professor));
  }

  previousState(): void {
    window.history.back();
  }
}
