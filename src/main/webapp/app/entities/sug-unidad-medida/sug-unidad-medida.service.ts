import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISugUnidadMedida } from 'app/shared/model/sug-unidad-medida.model';

type EntityResponseType = HttpResponse<ISugUnidadMedida>;
type EntityArrayResponseType = HttpResponse<ISugUnidadMedida[]>;

@Injectable({ providedIn: 'root' })
export class SugUnidadMedidaService {
  public resourceUrl = SERVER_API_URL + 'api/sug-unidad-medidas';

  constructor(protected http: HttpClient) {}

  create(sugUnidadMedida: ISugUnidadMedida): Observable<EntityResponseType> {
    return this.http.post<ISugUnidadMedida>(this.resourceUrl, sugUnidadMedida, { observe: 'response' });
  }

  update(sugUnidadMedida: ISugUnidadMedida): Observable<EntityResponseType> {
    return this.http.put<ISugUnidadMedida>(this.resourceUrl, sugUnidadMedida, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISugUnidadMedida>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISugUnidadMedida[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
