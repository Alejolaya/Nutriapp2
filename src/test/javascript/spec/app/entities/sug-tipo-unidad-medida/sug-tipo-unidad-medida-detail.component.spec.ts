/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugTipoUnidadMedidaDetailComponent } from 'app/entities/sug-tipo-unidad-medida/sug-tipo-unidad-medida-detail.component';
import { SugTipoUnidadMedida } from 'app/shared/model/sug-tipo-unidad-medida.model';

describe('Component Tests', () => {
  describe('SugTipoUnidadMedida Management Detail Component', () => {
    let comp: SugTipoUnidadMedidaDetailComponent;
    let fixture: ComponentFixture<SugTipoUnidadMedidaDetailComponent>;
    const route = ({ data: of({ sugTipoUnidadMedida: new SugTipoUnidadMedida(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugTipoUnidadMedidaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SugTipoUnidadMedidaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SugTipoUnidadMedidaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sugTipoUnidadMedida).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
