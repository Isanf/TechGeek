import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILevel, Level } from 'app/shared/model/level.model';
import { LevelService } from './level.service';
import { LevelComponent } from './level.component';
import { LevelDetailComponent } from './level-detail.component';
import { LevelUpdateComponent } from './level-update.component';

@Injectable({ providedIn: 'root' })
export class LevelResolve implements Resolve<ILevel> {
  constructor(private service: LevelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILevel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((level: HttpResponse<Level>) => {
          if (level.body) {
            return of(level.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Level());
  }
}

export const levelRoute: Routes = [
  {
    path: '',
    component: LevelComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Levels'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LevelDetailComponent,
    resolve: {
      level: LevelResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Levels'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LevelUpdateComponent,
    resolve: {
      level: LevelResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Levels'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LevelUpdateComponent,
    resolve: {
      level: LevelResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Levels'
    },
    canActivate: [UserRouteAccessService]
  }
];
