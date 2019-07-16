/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugMarcaUpdateComponent } from 'app/entities/sug-marca/sug-marca-update.component';
import { SugMarcaService } from 'app/entities/sug-marca/sug-marca.service';
import { SugMarca } from 'app/shared/model/sug-marca.model';

describe('Component Tests', () => {
  describe('SugMarca Management Update Component', () => {
    let comp: SugMarcaUpdateComponent;
    let fixture: ComponentFixture<SugMarcaUpdateComponent>;
    let service: SugMarcaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugMarcaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SugMarcaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugMarcaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugMarcaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SugMarca(123);
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
        const entity = new SugMarca();
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
