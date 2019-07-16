import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoUnidadMedida } from 'app/shared/model/tipo-unidad-medida.model';

type EntityResponseType = HttpResponse<ITipoUnidadMedida>;
type EntityArrayResponseType = HttpResponse<ITipoUnidadMedida[]>;

@Injectable({ providedIn: 'root' })
export class TipoUnidadMedidaService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-unidad-medidas';

  constructor(protected http: HttpClient) {}

  create(tipoUnidadMedida: ITipoUnidadMedida): Observable<EntityResponseType> {
    return this.http.post<ITipoUnidadMedida>(this.resourceUrl, tipoUnidadMedida, { observe: 'response' });
  }

  update(tipoUnidadMedida: ITipoUnidadMedida): Observable<EntityResponseType> {
    return this.http.put<ITipoUnidadMedida>(this.resourceUrl, tipoUnidadMedida, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoUnidadMedida>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoUnidadMedida[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
