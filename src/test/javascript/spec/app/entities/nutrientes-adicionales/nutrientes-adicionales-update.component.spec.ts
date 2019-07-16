/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { NutrientesAdicionalesUpdateComponent } from 'app/entities/nutrientes-adicionales/nutrientes-adicionales-update.component';
import { NutrientesAdicionalesService } from 'app/entities/nutrientes-adicionales/nutrientes-adicionales.service';
import { NutrientesAdicionales } from 'app/shared/model/nutrientes-adicionales.model';

describe('Component Tests', () => {
  describe('NutrientesAdicionales Management Update Component', () => {
    let comp: NutrientesAdicionalesUpdateComponent;
    let fixture: ComponentFixture<NutrientesAdicionalesUpdateComponent>;
    let service: NutrientesAdicionalesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [NutrientesAdicionalesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NutrientesAdicionalesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NutrientesAdicionalesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NutrientesAdicionalesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NutrientesAdicionales(123);
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
        const entity = new NutrientesAdicionales();
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
