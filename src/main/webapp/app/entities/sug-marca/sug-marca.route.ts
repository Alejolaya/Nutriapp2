import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SugMarca } from 'app/shared/model/sug-marca.model';
import { SugMarcaService } from './sug-marca.service';
import { SugMarcaComponent } from './sug-marca.component';
import { SugMarcaDetailComponent } from './sug-marca-detail.component';
import { SugMarcaUpdateComponent } from './sug-marca-update.component';
import { SugMarcaDeletePopupComponent } from './sug-marca-delete-dialog.component';
import { ISugMarca } from 'app/shared/model/sug-marca.model';

@Injectable({ providedIn: 'root' })
export class SugMarcaResolve implements Resolve<ISugMarca> {
  constructor(private service: SugMarcaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISugMarca> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SugMarca>) => response.ok),
        map((sugMarca: HttpResponse<SugMarca>) => sugMarca.body)
      );
    }
    return of(new SugMarca());
  }
}

export const sugMarcaRoute: Routes = [
  {
    path: '',
    component: SugMarcaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugMarca.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SugMarcaDetailComponent,
    resolve: {
      sugMarca: SugMarcaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugMarca.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SugMarcaUpdateComponent,
    resolve: {
      sugMarca: SugMarcaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugMarca.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SugMarcaUpdateComponent,
    resolve: {
      sugMarca: SugMarcaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugMarca.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sugMarcaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SugMarcaDeletePopupComponent,
    resolve: {
      sugMarca: SugMarcaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugMarca.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
