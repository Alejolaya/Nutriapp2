/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NutriappTestModule } from '../../../test.module';
import { SugTipoUnidadMedidaComponent } from 'app/entities/sug-tipo-unidad-medida/sug-tipo-unidad-medida.component';
import { SugTipoUnidadMedidaService } from 'app/entities/sug-tipo-unidad-medida/sug-tipo-unidad-medida.service';
import { SugTipoUnidadMedida } from 'app/shared/model/sug-tipo-unidad-medida.model';

describe('Component Tests', () => {
  describe('SugTipoUnidadMedida Management Component', () => {
    let comp: SugTipoUnidadMedidaComponent;
    let fixture: ComponentFixture<SugTipoUnidadMedidaComponent>;
    let service: SugTipoUnidadMedidaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugTipoUnidadMedidaComponent],
        providers: []
      })
        .overrideTemplate(SugTipoUnidadMedidaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugTipoUnidadMedidaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugTipoUnidadMedidaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SugTipoUnidadMedida(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sugTipoUnidadMedidas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
