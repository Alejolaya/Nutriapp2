import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SugTags } from 'app/shared/model/sug-tags.model';
import { SugTagsService } from './sug-tags.service';
import { SugTagsComponent } from './sug-tags.component';
import { SugTagsDetailComponent } from './sug-tags-detail.component';
import { SugTagsUpdateComponent } from './sug-tags-update.component';
import { SugTagsDeletePopupComponent } from './sug-tags-delete-dialog.component';
import { ISugTags } from 'app/shared/model/sug-tags.model';

@Injectable({ providedIn: 'root' })
export class SugTagsResolve implements Resolve<ISugTags> {
  constructor(private service: SugTagsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISugTags> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SugTags>) => response.ok),
        map((sugTags: HttpResponse<SugTags>) => sugTags.body)
      );
    }
    return of(new SugTags());
  }
}

export const sugTagsRoute: Routes = [
  {
    path: '',
    component: SugTagsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugTags.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SugTagsDetailComponent,
    resolve: {
      sugTags: SugTagsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugTags.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SugTagsUpdateComponent,
    resolve: {
      sugTags: SugTagsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugTags.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SugTagsUpdateComponent,
    resolve: {
      sugTags: SugTagsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugTags.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sugTagsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SugTagsDeletePopupComponent,
    resolve: {
      sugTags: SugTagsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nutriappApp.sugTags.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
