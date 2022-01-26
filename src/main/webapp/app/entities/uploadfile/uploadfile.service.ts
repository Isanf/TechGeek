import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUploadfile } from 'app/shared/model/uploadfile.model';

type EntityResponseType = HttpResponse<IUploadfile>;
type EntityArrayResponseType = HttpResponse<IUploadfile[]>;

@Injectable({ providedIn: 'root' })
export class UploadfileService {
  public resourceUrl = SERVER_API_URL + 'api/uploadfiles';

  constructor(protected http: HttpClient) {}

  create(uploadfile: IUploadfile): Observable<EntityResponseType> {
    return this.http.post<IUploadfile>(this.resourceUrl, uploadfile, { observe: 'response' });
  }

  update(uploadfile: IUploadfile): Observable<EntityResponseType> {
    return this.http.put<IUploadfile>(this.resourceUrl, uploadfile, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUploadfile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUploadfile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
