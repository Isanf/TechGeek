import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVideo, Video } from 'app/shared/model/video.model';
import { VideoService } from './video.service';
import { VideoComponent } from './video.component';
import { VideoDetailComponent } from './video-detail.component';
import { VideoUpdateComponent } from './video-update.component';

@Injectable({ providedIn: 'root' })
export class VideoResolve implements Resolve<IVideo> {
  constructor(private service: VideoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVideo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((video: HttpResponse<Video>) => {
          if (video.body) {
            return of(video.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Video());
  }
}

export const videoRoute: Routes = [
  {
    path: '',
    component: VideoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Videos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VideoDetailComponent,
    resolve: {
      video: VideoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Videos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VideoUpdateComponent,
    resolve: {
      video: VideoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Videos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VideoUpdateComponent,
    resolve: {
      video: VideoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Videos'
    },
    canActivate: [UserRouteAccessService]
  }
];
