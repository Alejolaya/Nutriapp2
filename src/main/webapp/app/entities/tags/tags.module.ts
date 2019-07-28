import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NutriappSharedModule } from 'app/shared';
import {
  TagsComponent,
  TagsDetailComponent,
  TagsUpdateComponent,
  TagsDeletePopupComponent,
  TagsDeleteDialogComponent,
  tagsRoute,
  tagsPopupRoute
} from './';

const ENTITY_STATES = [...tagsRoute, ...tagsPopupRoute];

@NgModule({
  imports: [NutriappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [TagsComponent, TagsDetailComponent, TagsUpdateComponent, TagsDeleteDialogComponent, TagsDeletePopupComponent],
  entryComponents: [TagsComponent, TagsUpdateComponent, TagsDeleteDialogComponent, TagsDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutriappTagsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
