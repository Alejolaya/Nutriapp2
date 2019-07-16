/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugNutrientesAdicionalesUpdateComponent } from 'app/entities/sug-nutrientes-adicionales/sug-nutrientes-adicionales-update.component';
import { SugNutrientesAdicionalesService } from 'app/entities/sug-nutrientes-adicionales/sug-nutrientes-adicionales.service';
import { SugNutrientesAdicionales } from 'app/shared/model/sug-nutrientes-adicionales.model';

describe('Component Tests', () => {
  describe('SugNutrientesAdicionales Management Update Component', () => {
    let comp: SugNutrientesAdicionalesUpdateComponent;
    let fixture: ComponentFixture<SugNutrientesAdicionalesUpdateComponent>;
    let service: SugNutrientesAdicionalesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugNutrientesAdicionalesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SugNutrientesAdicionalesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugNutrientesAdicionalesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugNutrientesAdicionalesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SugNutrientesAdicionales(123);
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
        const entity = new SugNutrientesAdicionales();
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
