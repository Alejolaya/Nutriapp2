import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';
import { SugerenciaProductoService } from './sugerencia-producto.service';
import { SugerenciaProductoComponent } from './sugerencia-producto.component';
import { SugerenciaProductoDetailComponent } from './sugerencia-producto-detail.component';
import { SugerenciaProductoUpdateComponent } from './sugerencia-producto-update.component';
import { SugerenciaProductoDeletePopupComponent } from './sugerencia-producto-delete-dialog.component';
import { ISugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';

@Injectable({ providedIn: 'root' })
export class SugerenciaProductoResolve implements Resolve<ISugerenciaProducto> {
  constructor(private service: SugerenciaProductoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISugerenciaProducto> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SugerenciaProducto>) => response.ok),
        map((sugerenciaProducto: HttpResponse<SugerenciaProducto>) => sugerenciaProducto.body)
      );
    }
    return of(new SugerenciaProducto());
  }
}

export const sugerenciaProductoRoute: Routes = [
  {
    path: '',
    component: SugerenciaProductoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugerenciaProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SugerenciaProductoDetailComponent,
    resolve: {
      sugerenciaProducto: SugerenciaProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugerenciaProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SugerenciaProductoUpdateComponent,
    resolve: {
      sugerenciaProducto: SugerenciaProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugerenciaProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SugerenciaProductoUpdateComponent,
    resolve: {
      sugerenciaProducto: SugerenciaProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugerenciaProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sugerenciaProductoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SugerenciaProductoDeletePopupComponent,
    resolve: {
      sugerenciaProducto: SugerenciaProductoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugerenciaProducto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
