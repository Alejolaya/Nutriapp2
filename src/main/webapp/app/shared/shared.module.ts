import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NutriappSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [NutriappSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [NutriappSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutriappSharedModule {
  static forRoot() {
    return {
      ngModule: NutriappSharedModule
    };
  }
}
