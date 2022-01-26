import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUploadfile, Uploadfile } from 'app/shared/model/uploadfile.model';
import { UploadfileService } from './uploadfile.service';
import { UploadfileComponent } from './uploadfile.component';
import { UploadfileDetailComponent } from './uploadfile-detail.component';
import { UploadfileUpdateComponent } from './uploadfile-update.component';

@Injectable({ providedIn: 'root' })
export class UploadfileResolve implements Resolve<IUploadfile> {
  constructor(private service: UploadfileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUploadfile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((uploadfile: HttpResponse<Uploadfile>) => {
          if (uploadfile.body) {
            return of(uploadfile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Uploadfile());
  }
}

export const uploadfileRoute: Routes = [
  {
    path: '',
    component: UploadfileComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Uploadfiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UploadfileDetailComponent,
    resolve: {
      uploadfile: UploadfileResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Uploadfiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UploadfileUpdateComponent,
    resolve: {
      uploadfile: UploadfileResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Uploadfiles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UploadfileUpdateComponent,
    resolve: {
      uploadfile: UploadfileResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Uploadfiles'
    },
    canActivate: [UserRouteAccessService]
  }
];
