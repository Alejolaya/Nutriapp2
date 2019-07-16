import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SugNutrientesAdicionales } from 'app/shared/model/sug-nutrientes-adicionales.model';
import { SugNutrientesAdicionalesService } from './sug-nutrientes-adicionales.service';
import { SugNutrientesAdicionalesComponent } from './sug-nutrientes-adicionales.component';
import { SugNutrientesAdicionalesDetailComponent } from './sug-nutrientes-adicionales-detail.component';
import { SugNutrientesAdicionalesUpdateComponent } from './sug-nutrientes-adicionales-update.component';
import { SugNutrientesAdicionalesDeletePopupComponent } from './sug-nutrientes-adicionales-delete-dialog.component';
import { ISugNutrientesAdicionales } from 'app/shared/model/sug-nutrientes-adicionales.model';

@Injectable({ providedIn: 'root' })
export class SugNutrientesAdicionalesResolve implements Resolve<ISugNutrientesAdicionales> {
  constructor(private service: SugNutrientesAdicionalesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISugNutrientesAdicionales> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SugNutrientesAdicionales>) => response.ok),
        map((sugNutrientesAdicionales: HttpResponse<SugNutrientesAdicionales>) => sugNutrientesAdicionales.body)
      );
    }
    return of(new SugNutrientesAdicionales());
  }
}

export const sugNutrientesAdicionalesRoute: Routes = [
  {
    path: '',
    component: SugNutrientesAdicionalesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugNutrientesAdicionales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SugNutrientesAdicionalesDetailComponent,
    resolve: {
      sugNutrientesAdicionales: SugNutrientesAdicionalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugNutrientesAdicionales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SugNutrientesAdicionalesUpdateComponent,
    resolve: {
      sugNutrientesAdicionales: SugNutrientesAdicionalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugNutrientesAdicionales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SugNutrientesAdicionalesUpdateComponent,
    resolve: {
      sugNutrientesAdicionales: SugNutrientesAdicionalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugNutrientesAdicionales.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sugNutrientesAdicionalesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SugNutrientesAdicionalesDeletePopupComponent,
    resolve: {
      sugNutrientesAdicionales: SugNutrientesAdicionalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugNutrientesAdicionales.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
