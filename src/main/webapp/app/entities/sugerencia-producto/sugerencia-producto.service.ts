import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';

type EntityResponseType = HttpResponse<ISugerenciaProducto>;
type EntityArrayResponseType = HttpResponse<ISugerenciaProducto[]>;

@Injectable({ providedIn: 'root' })
export class SugerenciaProductoService {
  public resourceUrl = SERVER_API_URL + 'api/sugerencia-productos';

  constructor(protected http: HttpClient) {}

  create(sugerenciaProducto: ISugerenciaProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sugerenciaProducto);
    return this.http
      .post<ISugerenciaProducto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sugerenciaProducto: ISugerenciaProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sugerenciaProducto);
    return this.http
      .put<ISugerenciaProducto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISugerenciaProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISugerenciaProducto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(sugerenciaProducto: ISugerenciaProducto): ISugerenciaProducto {
    const copy: ISugerenciaProducto = Object.assign({}, sugerenciaProducto, {
      fechaCreacion:
        sugerenciaProducto.fechaCreacion != null && sugerenciaProducto.fechaCreacion.isValid()
          ? sugerenciaProducto.fechaCreacion.toJSON()
          : null,
      fechaUltimaModificacion:
        sugerenciaProducto.fechaUltimaModificacion != null && sugerenciaProducto.fechaUltimaModificacion.isValid()
          ? sugerenciaProducto.fechaUltimaModificacion.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaCreacion = res.body.fechaCreacion != null ? moment(res.body.fechaCreacion) : null;
      res.body.fechaUltimaModificacion = res.body.fechaUltimaModificacion != null ? moment(res.body.fechaUltimaModificacion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sugerenciaProducto: ISugerenciaProducto) => {
        sugerenciaProducto.fechaCreacion = sugerenciaProducto.fechaCreacion != null ? moment(sugerenciaProducto.fechaCreacion) : null;
        sugerenciaProducto.fechaUltimaModificacion =
          sugerenciaProducto.fechaUltimaModificacion != null ? moment(sugerenciaProducto.fechaUltimaModificacion) : null;
      });
    }
    return res;
  }
}
