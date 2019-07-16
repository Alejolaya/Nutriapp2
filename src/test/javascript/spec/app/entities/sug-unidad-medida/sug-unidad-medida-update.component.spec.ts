/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugUnidadMedidaUpdateComponent } from 'app/entities/sug-unidad-medida/sug-unidad-medida-update.component';
import { SugUnidadMedidaService } from 'app/entities/sug-unidad-medida/sug-unidad-medida.service';
import { SugUnidadMedida } from 'app/shared/model/sug-unidad-medida.model';

describe('Component Tests', () => {
  describe('SugUnidadMedida Management Update Component', () => {
    let comp: SugUnidadMedidaUpdateComponent;
    let fixture: ComponentFixture<SugUnidadMedidaUpdateComponent>;
    let service: SugUnidadMedidaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugUnidadMedidaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SugUnidadMedidaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugUnidadMedidaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugUnidadMedidaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SugUnidadMedida(123);
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
        const entity = new SugUnidadMedida();
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
