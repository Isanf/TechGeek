import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVolunteer } from 'app/shared/model/volunteer.model';

type EntityResponseType = HttpResponse<IVolunteer>;
type EntityArrayResponseType = HttpResponse<IVolunteer[]>;

@Injectable({ providedIn: 'root' })
export class VolunteerService {
  public resourceUrl = SERVER_API_URL + 'api/volunteers';

  constructor(protected http: HttpClient) {}

  create(volunteer: IVolunteer): Observable<EntityResponseType> {
    return this.http.post<IVolunteer>(this.resourceUrl, volunteer, { observe: 'response' });
  }

  update(volunteer: IVolunteer): Observable<EntityResponseType> {
    return this.http.put<IVolunteer>(this.resourceUrl, volunteer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVolunteer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVolunteer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
