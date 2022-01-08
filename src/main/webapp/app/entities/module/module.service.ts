import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IModule } from 'app/shared/model/module.model';

type EntityResponseType = HttpResponse<IModule>;
type EntityArrayResponseType = HttpResponse<IModule[]>;

@Injectable({ providedIn: 'root' })
export class ModuleService {
  public resourceUrl = SERVER_API_URL + 'api/modules';

  constructor(protected http: HttpClient) {}

  create(module: IModule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(module);
    return this.http
      .post<IModule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(module: IModule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(module);
    return this.http
      .put<IModule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IModule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findOne(id: number | undefined): Observable<EntityResponseType> {
    return this.http
      .get<IModule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IModule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(module: IModule): IModule {
    const copy: IModule = Object.assign({}, module, {
      startDate: module.startDate && module.startDate.isValid() ? module.startDate.toJSON() : undefined,
      endDate: module.endDate && module.endDate.isValid() ? module.endDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((module: IModule) => {
        module.startDate = module.startDate ? moment(module.startDate) : undefined;
        module.endDate = module.endDate ? moment(module.endDate) : undefined;
      });
    }
    return res;
  }
}
