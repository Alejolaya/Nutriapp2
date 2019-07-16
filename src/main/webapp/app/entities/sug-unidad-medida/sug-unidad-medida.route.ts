import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SugUnidadMedida } from 'app/shared/model/sug-unidad-medida.model';
import { SugUnidadMedidaService } from './sug-unidad-medida.service';
import { SugUnidadMedidaComponent } from './sug-unidad-medida.component';
import { SugUnidadMedidaDetailComponent } from './sug-unidad-medida-detail.component';
import { SugUnidadMedidaUpdateComponent } from './sug-unidad-medida-update.component';
import { SugUnidadMedidaDeletePopupComponent } from './sug-unidad-medida-delete-dialog.component';
import { ISugUnidadMedida } from 'app/shared/model/sug-unidad-medida.model';

@Injectable({ providedIn: 'root' })
export class SugUnidadMedidaResolve implements Resolve<ISugUnidadMedida> {
  constructor(private service: SugUnidadMedidaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISugUnidadMedida> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SugUnidadMedida>) => response.ok),
        map((sugUnidadMedida: HttpResponse<SugUnidadMedida>) => sugUnidadMedida.body)
      );
    }
    return of(new SugUnidadMedida());
  }
}

export const sugUnidadMedidaRoute: Routes = [
  {
    path: '',
    component: SugUnidadMedidaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SugUnidadMedidaDetailComponent,
    resolve: {
      sugUnidadMedida: SugUnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SugUnidadMedidaUpdateComponent,
    resolve: {
      sugUnidadMedida: SugUnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SugUnidadMedidaUpdateComponent,
    resolve: {
      sugUnidadMedida: SugUnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sugUnidadMedidaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SugUnidadMedidaDeletePopupComponent,
    resolve: {
      sugUnidadMedida: SugUnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
