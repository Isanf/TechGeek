import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInfluencer, Influencer } from 'app/shared/model/influencer.model';
import { InfluencerService } from './influencer.service';
import { InfluencerComponent } from './influencer.component';
import { InfluencerDetailComponent } from './influencer-detail.component';
import { InfluencerUpdateComponent } from './influencer-update.component';

@Injectable({ providedIn: 'root' })
export class InfluencerResolve implements Resolve<IInfluencer> {
  constructor(private service: InfluencerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInfluencer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((influencer: HttpResponse<Influencer>) => {
          if (influencer.body) {
            return of(influencer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Influencer());
  }
}

export const influencerRoute: Routes = [
  {
    path: '',
    component: InfluencerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Influencers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InfluencerDetailComponent,
    resolve: {
      influencer: InfluencerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Influencers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InfluencerUpdateComponent,
    resolve: {
      influencer: InfluencerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Influencers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InfluencerUpdateComponent,
    resolve: {
      influencer: InfluencerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Influencers'
    },
    canActivate: [UserRouteAccessService]
  }
];
