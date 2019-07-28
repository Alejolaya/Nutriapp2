/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NutriappTestModule } from '../../../test.module';
import { NutrientesAdicionalesComponent } from 'app/entities/nutrientes-adicionales/nutrientes-adicionales.component';
import { NutrientesAdicionalesService } from 'app/entities/nutrientes-adicionales/nutrientes-adicionales.service';
import { NutrientesAdicionales } from 'app/shared/model/nutrientes-adicionales.model';

describe('Component Tests', () => {
  describe('NutrientesAdicionales Management Component', () => {
    let comp: NutrientesAdicionalesComponent;
    let fixture: ComponentFixture<NutrientesAdicionalesComponent>;
    let service: NutrientesAdicionalesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [NutrientesAdicionalesComponent],
        providers: []
      })
        .overrideTemplate(NutrientesAdicionalesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NutrientesAdicionalesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NutrientesAdicionalesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NutrientesAdicionales(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.nutrientesAdicionales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
