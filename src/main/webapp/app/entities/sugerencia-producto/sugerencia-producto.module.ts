import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NutriappSharedModule } from 'app/shared';
import {
  SugerenciaProductoComponent,
  SugerenciaProductoDetailComponent,
  SugerenciaProductoUpdateComponent,
  SugerenciaProductoDeletePopupComponent,
  SugerenciaProductoDeleteDialogComponent,
  sugerenciaProductoRoute,
  sugerenciaProductoPopupRoute
} from './';

const ENTITY_STATES = [...sugerenciaProductoRoute, ...sugerenciaProductoPopupRoute];

@NgModule({
  imports: [NutriappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SugerenciaProductoComponent,
    SugerenciaProductoDetailComponent,
    SugerenciaProductoUpdateComponent,
    SugerenciaProductoDeleteDialogComponent,
    SugerenciaProductoDeletePopupComponent
  ],
  entryComponents: [
    SugerenciaProductoComponent,
    SugerenciaProductoUpdateComponent,
    SugerenciaProductoDeleteDialogComponent,
    SugerenciaProductoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutriappSugerenciaProductoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
