/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NutriappTestModule } from '../../../test.module';
import { SugCategoriaComponent } from 'app/entities/sug-categoria/sug-categoria.component';
import { SugCategoriaService } from 'app/entities/sug-categoria/sug-categoria.service';
import { SugCategoria } from 'app/shared/model/sug-categoria.model';

describe('Component Tests', () => {
  describe('SugCategoria Management Component', () => {
    let comp: SugCategoriaComponent;
    let fixture: ComponentFixture<SugCategoriaComponent>;
    let service: SugCategoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugCategoriaComponent],
        providers: []
      })
        .overrideTemplate(SugCategoriaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugCategoriaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugCategoriaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SugCategoria(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sugCategorias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
