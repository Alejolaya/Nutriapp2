import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SugCategoria } from 'app/shared/model/sug-categoria.model';
import { SugCategoriaService } from './sug-categoria.service';
import { SugCategoriaComponent } from './sug-categoria.component';
import { SugCategoriaDetailComponent } from './sug-categoria-detail.component';
import { SugCategoriaUpdateComponent } from './sug-categoria-update.component';
import { SugCategoriaDeletePopupComponent } from './sug-categoria-delete-dialog.component';
import { ISugCategoria } from 'app/shared/model/sug-categoria.model';

@Injectable({ providedIn: 'root' })
export class SugCategoriaResolve implements Resolve<ISugCategoria> {
  constructor(private service: SugCategoriaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISugCategoria> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SugCategoria>) => response.ok),
        map((sugCategoria: HttpResponse<SugCategoria>) => sugCategoria.body)
      );
    }
    return of(new SugCategoria());
  }
}

export const sugCategoriaRoute: Routes = [
  {
    path: '',
    component: SugCategoriaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugCategoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SugCategoriaDetailComponent,
    resolve: {
      sugCategoria: SugCategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugCategoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SugCategoriaUpdateComponent,
    resolve: {
      sugCategoria: SugCategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugCategoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SugCategoriaUpdateComponent,
    resolve: {
      sugCategoria: SugCategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugCategoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sugCategoriaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SugCategoriaDeletePopupComponent,
    resolve: {
      sugCategoria: SugCategoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugCategoria.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
