/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { TipoUnidadMedidaDetailComponent } from 'app/entities/tipo-unidad-medida/tipo-unidad-medida-detail.component';
import { TipoUnidadMedida } from 'app/shared/model/tipo-unidad-medida.model';

describe('Component Tests', () => {
  describe('TipoUnidadMedida Management Detail Component', () => {
    let comp: TipoUnidadMedidaDetailComponent;
    let fixture: ComponentFixture<TipoUnidadMedidaDetailComponent>;
    const route = ({ data: of({ tipoUnidadMedida: new TipoUnidadMedida(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [TipoUnidadMedidaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoUnidadMedidaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoUnidadMedidaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoUnidadMedida).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
