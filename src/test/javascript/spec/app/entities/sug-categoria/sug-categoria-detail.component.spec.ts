/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugCategoriaDetailComponent } from 'app/entities/sug-categoria/sug-categoria-detail.component';
import { SugCategoria } from 'app/shared/model/sug-categoria.model';

describe('Component Tests', () => {
  describe('SugCategoria Management Detail Component', () => {
    let comp: SugCategoriaDetailComponent;
    let fixture: ComponentFixture<SugCategoriaDetailComponent>;
    const route = ({ data: of({ sugCategoria: new SugCategoria(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugCategoriaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SugCategoriaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SugCategoriaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sugCategoria).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
