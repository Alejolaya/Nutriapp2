import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SugTipoUnidadMedida } from 'app/shared/model/sug-tipo-unidad-medida.model';
import { SugTipoUnidadMedidaService } from './sug-tipo-unidad-medida.service';
import { SugTipoUnidadMedidaComponent } from './sug-tipo-unidad-medida.component';
import { SugTipoUnidadMedidaDetailComponent } from './sug-tipo-unidad-medida-detail.component';
import { SugTipoUnidadMedidaUpdateComponent } from './sug-tipo-unidad-medida-update.component';
import { SugTipoUnidadMedidaDeletePopupComponent } from './sug-tipo-unidad-medida-delete-dialog.component';
import { ISugTipoUnidadMedida } from 'app/shared/model/sug-tipo-unidad-medida.model';

@Injectable({ providedIn: 'root' })
export class SugTipoUnidadMedidaResolve implements Resolve<ISugTipoUnidadMedida> {
  constructor(private service: SugTipoUnidadMedidaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISugTipoUnidadMedida> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SugTipoUnidadMedida>) => response.ok),
        map((sugTipoUnidadMedida: HttpResponse<SugTipoUnidadMedida>) => sugTipoUnidadMedida.body)
      );
    }
    return of(new SugTipoUnidadMedida());
  }
}

export const sugTipoUnidadMedidaRoute: Routes = [
  {
    path: '',
    component: SugTipoUnidadMedidaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugTipoUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SugTipoUnidadMedidaDetailComponent,
    resolve: {
      sugTipoUnidadMedida: SugTipoUnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugTipoUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SugTipoUnidadMedidaUpdateComponent,
    resolve: {
      sugTipoUnidadMedida: SugTipoUnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugTipoUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SugTipoUnidadMedidaUpdateComponent,
    resolve: {
      sugTipoUnidadMedida: SugTipoUnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugTipoUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sugTipoUnidadMedidaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SugTipoUnidadMedidaDeletePopupComponent,
    resolve: {
      sugTipoUnidadMedida: SugTipoUnidadMedidaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugTipoUnidadMedida.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
