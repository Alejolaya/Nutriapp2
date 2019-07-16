/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NutriappTestModule } from '../../../test.module';
import { SugNutrientesAdicionalesComponent } from 'app/entities/sug-nutrientes-adicionales/sug-nutrientes-adicionales.component';
import { SugNutrientesAdicionalesService } from 'app/entities/sug-nutrientes-adicionales/sug-nutrientes-adicionales.service';
import { SugNutrientesAdicionales } from 'app/shared/model/sug-nutrientes-adicionales.model';

describe('Component Tests', () => {
  describe('SugNutrientesAdicionales Management Component', () => {
    let comp: SugNutrientesAdicionalesComponent;
    let fixture: ComponentFixture<SugNutrientesAdicionalesComponent>;
    let service: SugNutrientesAdicionalesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugNutrientesAdicionalesComponent],
        providers: []
      })
        .overrideTemplate(SugNutrientesAdicionalesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugNutrientesAdicionalesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugNutrientesAdicionalesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SugNutrientesAdicionales(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sugNutrientesAdicionales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
