import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NutriappSharedModule } from 'app/shared';
import {
  SugCategoriaComponent,
  SugCategoriaDetailComponent,
  SugCategoriaUpdateComponent,
  SugCategoriaDeletePopupComponent,
  SugCategoriaDeleteDialogComponent,
  sugCategoriaRoute,
  sugCategoriaPopupRoute
} from './';

const ENTITY_STATES = [...sugCategoriaRoute, ...sugCategoriaPopupRoute];

@NgModule({
  imports: [NutriappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SugCategoriaComponent,
    SugCategoriaDetailComponent,
    SugCategoriaUpdateComponent,
    SugCategoriaDeleteDialogComponent,
    SugCategoriaDeletePopupComponent
  ],
  entryComponents: [
    SugCategoriaComponent,
    SugCategoriaUpdateComponent,
    SugCategoriaDeleteDialogComponent,
    SugCategoriaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutriappSugCategoriaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
