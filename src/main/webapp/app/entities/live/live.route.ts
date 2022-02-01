import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILive, Live } from 'app/shared/model/live.model';
import { LiveService } from './live.service';
import { LiveComponent } from './live.component';
import { LiveDetailComponent } from './live-detail.component';
import { LiveUpdateComponent } from './live-update.component';

@Injectable({ providedIn: 'root' })
export class LiveResolve implements Resolve<ILive> {
  constructor(private service: LiveService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILive> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((live: HttpResponse<Live>) => {
          if (live.body) {
            return of(live.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Live());
  }
}

export const liveRoute: Routes = [
  {
    path: '',
    component: LiveComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Lives'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LiveDetailComponent,
    resolve: {
      live: LiveResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Lives'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LiveUpdateComponent,
    resolve: {
      live: LiveResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Lives'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LiveUpdateComponent,
    resolve: {
      live: LiveResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Lives'
    },
    canActivate: [UserRouteAccessService]
  }
];
