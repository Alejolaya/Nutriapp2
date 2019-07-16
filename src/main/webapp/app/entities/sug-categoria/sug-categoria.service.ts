import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISugCategoria } from 'app/shared/model/sug-categoria.model';

type EntityResponseType = HttpResponse<ISugCategoria>;
type EntityArrayResponseType = HttpResponse<ISugCategoria[]>;

@Injectable({ providedIn: 'root' })
export class SugCategoriaService {
  public resourceUrl = SERVER_API_URL + 'api/sug-categorias';

  constructor(protected http: HttpClient) {}

  create(sugCategoria: ISugCategoria): Observable<EntityResponseType> {
    return this.http.post<ISugCategoria>(this.resourceUrl, sugCategoria, { observe: 'response' });
  }

  update(sugCategoria: ISugCategoria): Observable<EntityResponseType> {
    return this.http.put<ISugCategoria>(this.resourceUrl, sugCategoria, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISugCategoria>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISugCategoria[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
