/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugCategoriaUpdateComponent } from 'app/entities/sug-categoria/sug-categoria-update.component';
import { SugCategoriaService } from 'app/entities/sug-categoria/sug-categoria.service';
import { SugCategoria } from 'app/shared/model/sug-categoria.model';

describe('Component Tests', () => {
  describe('SugCategoria Management Update Component', () => {
    let comp: SugCategoriaUpdateComponent;
    let fixture: ComponentFixture<SugCategoriaUpdateComponent>;
    let service: SugCategoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugCategoriaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SugCategoriaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugCategoriaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugCategoriaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SugCategoria(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SugCategoria();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
