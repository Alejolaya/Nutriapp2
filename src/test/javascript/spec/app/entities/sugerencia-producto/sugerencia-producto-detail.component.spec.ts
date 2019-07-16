/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugerenciaProductoDetailComponent } from 'app/entities/sugerencia-producto/sugerencia-producto-detail.component';
import { SugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';

describe('Component Tests', () => {
  describe('SugerenciaProducto Management Detail Component', () => {
    let comp: SugerenciaProductoDetailComponent;
    let fixture: ComponentFixture<SugerenciaProductoDetailComponent>;
    const route = ({ data: of({ sugerenciaProducto: new SugerenciaProducto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugerenciaProductoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SugerenciaProductoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SugerenciaProductoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sugerenciaProducto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
