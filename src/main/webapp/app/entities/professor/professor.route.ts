import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProfessor, Professor } from 'app/shared/model/professor.model';
import { ProfessorService } from './professor.service';
import { ProfessorComponent } from './professor.component';
import { ProfessorDetailComponent } from './professor-detail.component';
import { ProfessorUpdateComponent } from './professor-update.component';

@Injectable({ providedIn: 'root' })
export class ProfessorResolve implements Resolve<IProfessor> {
  constructor(private service: ProfessorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfessor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((professor: HttpResponse<Professor>) => {
          if (professor.body) {
            return of(professor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Professor());
  }
}

export const professorRoute: Routes = [
  {
    path: '',
    component: ProfessorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Professors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfessorDetailComponent,
    resolve: {
      professor: ProfessorResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Professors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfessorUpdateComponent,
    resolve: {
      professor: ProfessorResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Professors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfessorUpdateComponent,
    resolve: {
      professor: ProfessorResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Professors'
    },
    canActivate: [UserRouteAccessService]
  }
];
