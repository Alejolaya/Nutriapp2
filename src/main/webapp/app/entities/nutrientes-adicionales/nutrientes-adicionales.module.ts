import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NutriappSharedModule } from 'app/shared';
import {
  NutrientesAdicionalesComponent,
  NutrientesAdicionalesDetailComponent,
  NutrientesAdicionalesUpdateComponent,
  NutrientesAdicionalesDeletePopupComponent,
  NutrientesAdicionalesDeleteDialogComponent,
  nutrientesAdicionalesRoute,
  nutrientesAdicionalesPopupRoute
} from './';

const ENTITY_STATES = [...nutrientesAdicionalesRoute, ...nutrientesAdicionalesPopupRoute];

@NgModule({
  imports: [NutriappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NutrientesAdicionalesComponent,
    NutrientesAdicionalesDetailComponent,
    NutrientesAdicionalesUpdateComponent,
    NutrientesAdicionalesDeleteDialogComponent,
    NutrientesAdicionalesDeletePopupComponent
  ],
  entryComponents: [
    NutrientesAdicionalesComponent,
    NutrientesAdicionalesUpdateComponent,
    NutrientesAdicionalesDeleteDialogComponent,
    NutrientesAdicionalesDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutriappNutrientesAdicionalesModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
