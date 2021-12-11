import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFramework } from 'app/shared/model/framework.model';

type EntityResponseType = HttpResponse<IFramework>;
type EntityArrayResponseType = HttpResponse<IFramework[]>;

@Injectable({ providedIn: 'root' })
export class FrameworkService {
  public resourceUrl = SERVER_API_URL + 'api/frameworks';

  constructor(protected http: HttpClient) {}

  create(framework: IFramework): Observable<EntityResponseType> {
    return this.http.post<IFramework>(this.resourceUrl, framework, { observe: 'response' });
  }

  update(framework: IFramework): Observable<EntityResponseType> {
    return this.http.put<IFramework>(this.resourceUrl, framework, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFramework>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFramework[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
