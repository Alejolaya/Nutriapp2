import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NutriappSharedModule } from 'app/shared';
import {
  SugTagsComponent,
  SugTagsDetailComponent,
  SugTagsUpdateComponent,
  SugTagsDeletePopupComponent,
  SugTagsDeleteDialogComponent,
  sugTagsRoute,
  sugTagsPopupRoute
} from './';

const ENTITY_STATES = [...sugTagsRoute, ...sugTagsPopupRoute];

@NgModule({
  imports: [NutriappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SugTagsComponent,
    SugTagsDetailComponent,
    SugTagsUpdateComponent,
    SugTagsDeleteDialogComponent,
    SugTagsDeletePopupComponent
  ],
  entryComponents: [SugTagsComponent, SugTagsUpdateComponent, SugTagsDeleteDialogComponent, SugTagsDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutriappSugTagsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
