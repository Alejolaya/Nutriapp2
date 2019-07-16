/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NutriappTestModule } from '../../../test.module';
import { SugUnidadMedidaComponent } from 'app/entities/sug-unidad-medida/sug-unidad-medida.component';
import { SugUnidadMedidaService } from 'app/entities/sug-unidad-medida/sug-unidad-medida.service';
import { SugUnidadMedida } from 'app/shared/model/sug-unidad-medida.model';

describe('Component Tests', () => {
  describe('SugUnidadMedida Management Component', () => {
    let comp: SugUnidadMedidaComponent;
    let fixture: ComponentFixture<SugUnidadMedidaComponent>;
    let service: SugUnidadMedidaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugUnidadMedidaComponent],
        providers: []
      })
        .overrideTemplate(SugUnidadMedidaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugUnidadMedidaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugUnidadMedidaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SugUnidadMedida(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sugUnidadMedidas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
