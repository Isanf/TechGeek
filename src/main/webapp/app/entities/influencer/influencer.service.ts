import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInfluencer } from 'app/shared/model/influencer.model';

type EntityResponseType = HttpResponse<IInfluencer>;
type EntityArrayResponseType = HttpResponse<IInfluencer[]>;

@Injectable({ providedIn: 'root' })
export class InfluencerService {
  public resourceUrl = SERVER_API_URL + 'api/influencers';

  constructor(protected http: HttpClient) {}

  create(influencer: IInfluencer): Observable<EntityResponseType> {
    return this.http.post<IInfluencer>(this.resourceUrl, influencer, { observe: 'response' });
  }

  update(influencer: IInfluencer): Observable<EntityResponseType> {
    return this.http.put<IInfluencer>(this.resourceUrl, influencer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInfluencer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInfluencer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
