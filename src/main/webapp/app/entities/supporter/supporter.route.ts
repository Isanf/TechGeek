import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISupporter, Supporter } from 'app/shared/model/supporter.model';
import { SupporterService } from './supporter.service';
import { SupporterComponent } from './supporter.component';
import { SupporterDetailComponent } from './supporter-detail.component';
import { SupporterUpdateComponent } from './supporter-update.component';

@Injectable({ providedIn: 'root' })
export class SupporterResolve implements Resolve<ISupporter> {
  constructor(private service: SupporterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISupporter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((supporter: HttpResponse<Supporter>) => {
          if (supporter.body) {
            return of(supporter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Supporter());
  }
}

export const supporterRoute: Routes = [
  {
    path: '',
    component: SupporterComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Supporters'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SupporterDetailComponent,
    resolve: {
      supporter: SupporterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Supporters'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SupporterUpdateComponent,
    resolve: {
      supporter: SupporterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Supporters'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SupporterUpdateComponent,
    resolve: {
      supporter: SupporterResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Supporters'
    },
    canActivate: [UserRouteAccessService]
  }
];
