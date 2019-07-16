/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugNutrientesAdicionalesDetailComponent } from 'app/entities/sug-nutrientes-adicionales/sug-nutrientes-adicionales-detail.component';
import { SugNutrientesAdicionales } from 'app/shared/model/sug-nutrientes-adicionales.model';

describe('Component Tests', () => {
  describe('SugNutrientesAdicionales Management Detail Component', () => {
    let comp: SugNutrientesAdicionalesDetailComponent;
    let fixture: ComponentFixture<SugNutrientesAdicionalesDetailComponent>;
    const route = ({ data: of({ sugNutrientesAdicionales: new SugNutrientesAdicionales(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugNutrientesAdicionalesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SugNutrientesAdicionalesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SugNutrientesAdicionalesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sugNutrientesAdicionales).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
