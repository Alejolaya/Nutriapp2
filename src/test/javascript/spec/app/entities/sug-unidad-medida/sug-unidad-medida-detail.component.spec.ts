/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugUnidadMedidaDetailComponent } from 'app/entities/sug-unidad-medida/sug-unidad-medida-detail.component';
import { SugUnidadMedida } from 'app/shared/model/sug-unidad-medida.model';

describe('Component Tests', () => {
  describe('SugUnidadMedida Management Detail Component', () => {
    let comp: SugUnidadMedidaDetailComponent;
    let fixture: ComponentFixture<SugUnidadMedidaDetailComponent>;
    const route = ({ data: of({ sugUnidadMedida: new SugUnidadMedida(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugUnidadMedidaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SugUnidadMedidaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SugUnidadMedidaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sugUnidadMedida).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
