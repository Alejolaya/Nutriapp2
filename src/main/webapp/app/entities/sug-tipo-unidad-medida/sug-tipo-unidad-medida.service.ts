import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISugTipoUnidadMedida } from 'app/shared/model/sug-tipo-unidad-medida.model';

type EntityResponseType = HttpResponse<ISugTipoUnidadMedida>;
type EntityArrayResponseType = HttpResponse<ISugTipoUnidadMedida[]>;

@Injectable({ providedIn: 'root' })
export class SugTipoUnidadMedidaService {
  public resourceUrl = SERVER_API_URL + 'api/sug-tipo-unidad-medidas';

  constructor(protected http: HttpClient) {}

  create(sugTipoUnidadMedida: ISugTipoUnidadMedida): Observable<EntityResponseType> {
    return this.http.post<ISugTipoUnidadMedida>(this.resourceUrl, sugTipoUnidadMedida, { observe: 'response' });
  }

  update(sugTipoUnidadMedida: ISugTipoUnidadMedida): Observable<EntityResponseType> {
    return this.http.put<ISugTipoUnidadMedida>(this.resourceUrl, sugTipoUnidadMedida, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISugTipoUnidadMedida>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISugTipoUnidadMedida[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
