/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NutriappTestModule } from '../../../test.module';
import { TipoUnidadMedidaComponent } from 'app/entities/tipo-unidad-medida/tipo-unidad-medida.component';
import { TipoUnidadMedidaService } from 'app/entities/tipo-unidad-medida/tipo-unidad-medida.service';
import { TipoUnidadMedida } from 'app/shared/model/tipo-unidad-medida.model';

describe('Component Tests', () => {
  describe('TipoUnidadMedida Management Component', () => {
    let comp: TipoUnidadMedidaComponent;
    let fixture: ComponentFixture<TipoUnidadMedidaComponent>;
    let service: TipoUnidadMedidaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [TipoUnidadMedidaComponent],
        providers: []
      })
        .overrideTemplate(TipoUnidadMedidaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoUnidadMedidaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoUnidadMedidaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoUnidadMedida(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoUnidadMedidas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
