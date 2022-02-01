import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISupporter } from 'app/shared/model/supporter.model';

type EntityResponseType = HttpResponse<ISupporter>;
type EntityArrayResponseType = HttpResponse<ISupporter[]>;

@Injectable({ providedIn: 'root' })
export class SupporterService {
  public resourceUrl = SERVER_API_URL + 'api/supporters';

  constructor(protected http: HttpClient) {}

  create(supporter: ISupporter): Observable<EntityResponseType> {
    return this.http.post<ISupporter>(this.resourceUrl, supporter, { observe: 'response' });
  }

  update(supporter: ISupporter): Observable<EntityResponseType> {
    return this.http.put<ISupporter>(this.resourceUrl, supporter, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISupporter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISupporter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
