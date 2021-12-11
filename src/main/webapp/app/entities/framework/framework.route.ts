import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFramework, Framework } from 'app/shared/model/framework.model';
import { FrameworkService } from './framework.service';
import { FrameworkComponent } from './framework.component';
import { FrameworkDetailComponent } from './framework-detail.component';
import { FrameworkUpdateComponent } from './framework-update.component';

@Injectable({ providedIn: 'root' })
export class FrameworkResolve implements Resolve<IFramework> {
  constructor(private service: FrameworkService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFramework> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((framework: HttpResponse<Framework>) => {
          if (framework.body) {
            return of(framework.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Framework());
  }
}

export const frameworkRoute: Routes = [
  {
    path: '',
    component: FrameworkComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Frameworks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FrameworkDetailComponent,
    resolve: {
      framework: FrameworkResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Frameworks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FrameworkUpdateComponent,
    resolve: {
      framework: FrameworkResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Frameworks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FrameworkUpdateComponent,
    resolve: {
      framework: FrameworkResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Frameworks'
    },
    canActivate: [UserRouteAccessService]
  }
];
