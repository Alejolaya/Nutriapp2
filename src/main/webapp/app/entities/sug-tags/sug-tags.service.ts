import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISugTags } from 'app/shared/model/sug-tags.model';

type EntityResponseType = HttpResponse<ISugTags>;
type EntityArrayResponseType = HttpResponse<ISugTags[]>;

@Injectable({ providedIn: 'root' })
export class SugTagsService {
  public resourceUrl = SERVER_API_URL + 'api/sug-tags';

  constructor(protected http: HttpClient) {}

  create(sugTags: ISugTags): Observable<EntityResponseType> {
    return this.http.post<ISugTags>(this.resourceUrl, sugTags, { observe: 'response' });
  }

  update(sugTags: ISugTags): Observable<EntityResponseType> {
    return this.http.put<ISugTags>(this.resourceUrl, sugTags, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISugTags>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISugTags[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
