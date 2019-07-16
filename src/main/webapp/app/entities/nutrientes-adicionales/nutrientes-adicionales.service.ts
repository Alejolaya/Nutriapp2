import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INutrientesAdicionales } from 'app/shared/model/nutrientes-adicionales.model';

type EntityResponseType = HttpResponse<INutrientesAdicionales>;
type EntityArrayResponseType = HttpResponse<INutrientesAdicionales[]>;

@Injectable({ providedIn: 'root' })
export class NutrientesAdicionalesService {
  public resourceUrl = SERVER_API_URL + 'api/nutrientes-adicionales';

  constructor(protected http: HttpClient) {}

  create(nutrientesAdicionales: INutrientesAdicionales): Observable<EntityResponseType> {
    return this.http.post<INutrientesAdicionales>(this.resourceUrl, nutrientesAdicionales, { observe: 'response' });
  }

  update(nutrientesAdicionales: INutrientesAdicionales): Observable<EntityResponseType> {
    return this.http.put<INutrientesAdicionales>(this.resourceUrl, nutrientesAdicionales, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INutrientesAdicionales>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INutrientesAdicionales[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
