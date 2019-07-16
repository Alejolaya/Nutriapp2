/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NutriappTestModule } from '../../../test.module';
import { SugerenciaProductoComponent } from 'app/entities/sugerencia-producto/sugerencia-producto.component';
import { SugerenciaProductoService } from 'app/entities/sugerencia-producto/sugerencia-producto.service';
import { SugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';

describe('Component Tests', () => {
  describe('SugerenciaProducto Management Component', () => {
    let comp: SugerenciaProductoComponent;
    let fixture: ComponentFixture<SugerenciaProductoComponent>;
    let service: SugerenciaProductoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugerenciaProductoComponent],
        providers: []
      })
        .overrideTemplate(SugerenciaProductoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugerenciaProductoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugerenciaProductoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SugerenciaProducto(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sugerenciaProductos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
