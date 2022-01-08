import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILive } from 'app/shared/model/live.model';

type EntityResponseType = HttpResponse<ILive>;
type EntityArrayResponseType = HttpResponse<ILive[]>;

@Injectable({ providedIn: 'root' })
export class LiveService {
  public resourceUrl = SERVER_API_URL + 'api/lives';

  constructor(protected http: HttpClient) {}

  create(live: ILive): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(live);
    return this.http
      .post<ILive>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(live: ILive): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(live);
    return this.http
      .put<ILive>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILive>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findLast(): Observable<EntityResponseType> {
    return this.http
      .get<ILive>(`${this.resourceUrl}/Last`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findToday(): Observable<EntityResponseType> {
    return this.http
      .get<ILive>(`${this.resourceUrl}/Today`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILive[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(live: ILive): ILive {
    const copy: ILive = Object.assign({}, live, {
      startDate: live.startDate && live.startDate.isValid() ? live.startDate.toJSON() : undefined,
      endDate: live.endDate && live.endDate.isValid() ? live.endDate.toJSON() : undefined
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
      res.body.forEach((live: ILive) => {
        live.startDate = live.startDate ? moment(live.startDate) : undefined;
        live.endDate = live.endDate ? moment(live.endDate) : undefined;
      });
    }
    return res;
  }
}
