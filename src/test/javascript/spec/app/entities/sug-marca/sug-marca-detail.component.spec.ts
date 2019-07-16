/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugMarcaDetailComponent } from 'app/entities/sug-marca/sug-marca-detail.component';
import { SugMarca } from 'app/shared/model/sug-marca.model';

describe('Component Tests', () => {
  describe('SugMarca Management Detail Component', () => {
    let comp: SugMarcaDetailComponent;
    let fixture: ComponentFixture<SugMarcaDetailComponent>;
    const route = ({ data: of({ sugMarca: new SugMarca(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugMarcaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SugMarcaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SugMarcaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sugMarca).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
