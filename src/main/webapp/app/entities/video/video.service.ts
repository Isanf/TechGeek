import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVideo } from 'app/shared/model/video.model';

type EntityResponseType = HttpResponse<IVideo>;
type EntityArrayResponseType = HttpResponse<IVideo[]>;

@Injectable({ providedIn: 'root' })
export class VideoService {
  public resourceUrl = SERVER_API_URL + 'api/videos';

  constructor(protected http: HttpClient) {}

  create(video: IVideo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(video);
    return this.http
      .post<IVideo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(video: IVideo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(video);
    return this.http
      .put<IVideo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVideo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVideo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(video: IVideo): IVideo {
    const copy: IVideo = Object.assign({}, video, {
      startDate: video.startDate && video.startDate.isValid() ? video.startDate.toJSON() : undefined,
      endDate: video.endDate && video.endDate.isValid() ? video.endDate.toJSON() : undefined
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
      res.body.forEach((video: IVideo) => {
        video.startDate = video.startDate ? moment(video.startDate) : undefined;
        video.endDate = video.endDate ? moment(video.endDate) : undefined;
      });
    }
    return res;
  }
}
