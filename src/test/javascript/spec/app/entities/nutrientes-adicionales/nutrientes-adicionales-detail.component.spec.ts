/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { NutrientesAdicionalesDetailComponent } from 'app/entities/nutrientes-adicionales/nutrientes-adicionales-detail.component';
import { NutrientesAdicionales } from 'app/shared/model/nutrientes-adicionales.model';

describe('Component Tests', () => {
  describe('NutrientesAdicionales Management Detail Component', () => {
    let comp: NutrientesAdicionalesDetailComponent;
    let fixture: ComponentFixture<NutrientesAdicionalesDetailComponent>;
    const route = ({ data: of({ nutrientesAdicionales: new NutrientesAdicionales(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [NutrientesAdicionalesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NutrientesAdicionalesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NutrientesAdicionalesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nutrientesAdicionales).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
