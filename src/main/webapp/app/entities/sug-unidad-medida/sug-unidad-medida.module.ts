import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NutriappSharedModule } from 'app/shared';
import {
  SugUnidadMedidaComponent,
  SugUnidadMedidaDetailComponent,
  SugUnidadMedidaUpdateComponent,
  SugUnidadMedidaDeletePopupComponent,
  SugUnidadMedidaDeleteDialogComponent,
  sugUnidadMedidaRoute,
  sugUnidadMedidaPopupRoute
} from './';

const ENTITY_STATES = [...sugUnidadMedidaRoute, ...sugUnidadMedidaPopupRoute];

@NgModule({
  imports: [NutriappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SugUnidadMedidaComponent,
    SugUnidadMedidaDetailComponent,
    SugUnidadMedidaUpdateComponent,
    SugUnidadMedidaDeleteDialogComponent,
    SugUnidadMedidaDeletePopupComponent
  ],
  entryComponents: [
    SugUnidadMedidaComponent,
    SugUnidadMedidaUpdateComponent,
    SugUnidadMedidaDeleteDialogComponent,
    SugUnidadMedidaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutriappSugUnidadMedidaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
