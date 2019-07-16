import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NutrientesAdicionales } from 'app/shared/model/nutrientes-adicionales.model';
import { NutrientesAdicionalesService } from './nutrientes-adicionales.service';
import { NutrientesAdicionalesComponent } from './nutrientes-adicionales.component';
import { NutrientesAdicionalesDetailComponent } from './nutrientes-adicionales-detail.component';
import { NutrientesAdicionalesUpdateComponent } from './nutrientes-adicionales-update.component';
import { NutrientesAdicionalesDeletePopupComponent } from './nutrientes-adicionales-delete-dialog.component';
import { INutrientesAdicionales } from 'app/shared/model/nutrientes-adicionales.model';

@Injectable({ providedIn: 'root' })
export class NutrientesAdicionalesResolve implements Resolve<INutrientesAdicionales> {
  constructor(private service: NutrientesAdicionalesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INutrientesAdicionales> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<NutrientesAdicionales>) => response.ok),
        map((nutrientesAdicionales: HttpResponse<NutrientesAdicionales>) => nutrientesAdicionales.body)
      );
    }
    return of(new NutrientesAdicionales());
  }
}

export const nutrientesAdicionalesRoute: Routes = [
  {
    path: '',
    component: NutrientesAdicionalesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.nutrientesAdicionales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NutrientesAdicionalesDetailComponent,
    resolve: {
      nutrientesAdicionales: NutrientesAdicionalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.nutrientesAdicionales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NutrientesAdicionalesUpdateComponent,
    resolve: {
      nutrientesAdicionales: NutrientesAdicionalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.nutrientesAdicionales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NutrientesAdicionalesUpdateComponent,
    resolve: {
      nutrientesAdicionales: NutrientesAdicionalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.nutrientesAdicionales.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const nutrientesAdicionalesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NutrientesAdicionalesDeletePopupComponent,
    resolve: {
      nutrientesAdicionales: NutrientesAdicionalesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.nutrientesAdicionales.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
