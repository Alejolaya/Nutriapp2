import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NutriappSharedModule } from 'app/shared';
import {
  SugNutrientesAdicionalesComponent,
  SugNutrientesAdicionalesDetailComponent,
  SugNutrientesAdicionalesUpdateComponent,
  SugNutrientesAdicionalesDeletePopupComponent,
  SugNutrientesAdicionalesDeleteDialogComponent,
  sugNutrientesAdicionalesRoute,
  sugNutrientesAdicionalesPopupRoute
} from './';

const ENTITY_STATES = [...sugNutrientesAdicionalesRoute, ...sugNutrientesAdicionalesPopupRoute];

@NgModule({
  imports: [NutriappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SugNutrientesAdicionalesComponent,
    SugNutrientesAdicionalesDetailComponent,
    SugNutrientesAdicionalesUpdateComponent,
    SugNutrientesAdicionalesDeleteDialogComponent,
    SugNutrientesAdicionalesDeletePopupComponent
  ],
  entryComponents: [
    SugNutrientesAdicionalesComponent,
    SugNutrientesAdicionalesUpdateComponent,
    SugNutrientesAdicionalesDeleteDialogComponent,
    SugNutrientesAdicionalesDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutriappSugNutrientesAdicionalesModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
