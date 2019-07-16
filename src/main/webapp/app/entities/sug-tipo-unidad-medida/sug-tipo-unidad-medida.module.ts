import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NutriappSharedModule } from 'app/shared';
import {
  SugTipoUnidadMedidaComponent,
  SugTipoUnidadMedidaDetailComponent,
  SugTipoUnidadMedidaUpdateComponent,
  SugTipoUnidadMedidaDeletePopupComponent,
  SugTipoUnidadMedidaDeleteDialogComponent,
  sugTipoUnidadMedidaRoute,
  sugTipoUnidadMedidaPopupRoute
} from './';

const ENTITY_STATES = [...sugTipoUnidadMedidaRoute, ...sugTipoUnidadMedidaPopupRoute];

@NgModule({
  imports: [NutriappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SugTipoUnidadMedidaComponent,
    SugTipoUnidadMedidaDetailComponent,
    SugTipoUnidadMedidaUpdateComponent,
    SugTipoUnidadMedidaDeleteDialogComponent,
    SugTipoUnidadMedidaDeletePopupComponent
  ],
  entryComponents: [
    SugTipoUnidadMedidaComponent,
    SugTipoUnidadMedidaUpdateComponent,
    SugTipoUnidadMedidaDeleteDialogComponent,
    SugTipoUnidadMedidaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutriappSugTipoUnidadMedidaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
