import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NutriappSharedModule } from 'app/shared';
import {
  SugMarcaComponent,
  SugMarcaDetailComponent,
  SugMarcaUpdateComponent,
  SugMarcaDeletePopupComponent,
  SugMarcaDeleteDialogComponent,
  sugMarcaRoute,
  sugMarcaPopupRoute
} from './';

const ENTITY_STATES = [...sugMarcaRoute, ...sugMarcaPopupRoute];

@NgModule({
  imports: [NutriappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SugMarcaComponent,
    SugMarcaDetailComponent,
    SugMarcaUpdateComponent,
    SugMarcaDeleteDialogComponent,
    SugMarcaDeletePopupComponent
  ],
  entryComponents: [SugMarcaComponent, SugMarcaUpdateComponent, SugMarcaDeleteDialogComponent, SugMarcaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutriappSugMarcaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
