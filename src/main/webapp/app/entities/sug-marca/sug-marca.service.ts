import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISugMarca } from 'app/shared/model/sug-marca.model';

type EntityResponseType = HttpResponse<ISugMarca>;
type EntityArrayResponseType = HttpResponse<ISugMarca[]>;

@Injectable({ providedIn: 'root' })
export class SugMarcaService {
  public resourceUrl = SERVER_API_URL + 'api/sug-marcas';

  constructor(protected http: HttpClient) {}

  create(sugMarca: ISugMarca): Observable<EntityResponseType> {
    return this.http.post<ISugMarca>(this.resourceUrl, sugMarca, { observe: 'response' });
  }

  update(sugMarca: ISugMarca): Observable<EntityResponseType> {
    return this.http.put<ISugMarca>(this.resourceUrl, sugMarca, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISugMarca>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISugMarca[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
