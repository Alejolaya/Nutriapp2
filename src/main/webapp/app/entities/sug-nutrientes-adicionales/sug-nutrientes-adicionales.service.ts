import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISugNutrientesAdicionales } from 'app/shared/model/sug-nutrientes-adicionales.model';

type EntityResponseType = HttpResponse<ISugNutrientesAdicionales>;
type EntityArrayResponseType = HttpResponse<ISugNutrientesAdicionales[]>;

@Injectable({ providedIn: 'root' })
export class SugNutrientesAdicionalesService {
  public resourceUrl = SERVER_API_URL + 'api/sug-nutrientes-adicionales';

  constructor(protected http: HttpClient) {}

  create(sugNutrientesAdicionales: ISugNutrientesAdicionales): Observable<EntityResponseType> {
    return this.http.post<ISugNutrientesAdicionales>(this.resourceUrl, sugNutrientesAdicionales, { observe: 'response' });
  }

  update(sugNutrientesAdicionales: ISugNutrientesAdicionales): Observable<EntityResponseType> {
    return this.http.put<ISugNutrientesAdicionales>(this.resourceUrl, sugNutrientesAdicionales, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISugNutrientesAdicionales>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISugNutrientesAdicionales[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
