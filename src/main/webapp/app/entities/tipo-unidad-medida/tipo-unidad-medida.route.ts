import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoUnidadMedida } from 'app/shared/model/tipo-unidad-medida.model';
import { TipoUnidadMedidaService } from './tipo-unidad-medida.service';
import { TipoUnidadMedidaComponent } from './tipo-unidad-medida.component';
import { TipoUnidadMedidaDetailComponent } from './tipo-unidad-medida-detail.component';
import { TipoUnidadMedidaUpdateComponent } from './tipo-unidad-medida-update.component';
import { TipoUnidadMedidaDeletePopupComponent } from './tipo-unidad-medida-delete-dialog.component';
import { ITipoUnidadMedida } from 'app/shared/model/tipo-unidad-medida.model';

@Injectable({ providedIn: 'root' })
export class TipoUnidadMedidaResolve implements Resolve<ITipoUnidadMedida> {
  constructor(private service: TipoUnidadMedidaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoUnidadMedida> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TipoUnidadMedida>) => response.ok),
        map((tipoUnidadMedida: HttpResponse<TipoUnidadMedida>) => tipoUnidadMedida.body)
      );
    }
    return of(new TipoUnidadMedida());
  }
}

export const tipoUnidadMedidaRoute: Routes = [
  {
    path: '',
    component: TipoUnidadMedidaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.tipoUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoUnidadMedidaDetailComponent,
    resolve: {
      tipoUnidadMedida: TipoUnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.tipoUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoUnidadMedidaUpdateComponent,
    resolve: {
      tipoUnidadMedida: TipoUnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.tipoUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoUnidadMedidaUpdateComponent,
    resolve: {
      tipoUnidadMedida: TipoUnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.tipoUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipoUnidadMedidaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipoUnidadMedidaDeletePopupComponent,
    resolve: {
      tipoUnidadMedida: TipoUnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.tipoUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
