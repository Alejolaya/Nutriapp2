/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NutriappTestModule } from '../../../test.module';
import { SugMarcaComponent } from 'app/entities/sug-marca/sug-marca.component';
import { SugMarcaService } from 'app/entities/sug-marca/sug-marca.service';
import { SugMarca } from 'app/shared/model/sug-marca.model';

describe('Component Tests', () => {
  describe('SugMarca Management Component', () => {
    let comp: SugMarcaComponent;
    let fixture: ComponentFixture<SugMarcaComponent>;
    let service: SugMarcaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugMarcaComponent],
        providers: []
      })
        .overrideTemplate(SugMarcaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugMarcaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugMarcaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SugMarca(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sugMarcas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
