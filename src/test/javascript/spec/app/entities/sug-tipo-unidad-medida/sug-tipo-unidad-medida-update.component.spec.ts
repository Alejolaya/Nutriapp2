/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugTipoUnidadMedidaUpdateComponent } from 'app/entities/sug-tipo-unidad-medida/sug-tipo-unidad-medida-update.component';
import { SugTipoUnidadMedidaService } from 'app/entities/sug-tipo-unidad-medida/sug-tipo-unidad-medida.service';
import { SugTipoUnidadMedida } from 'app/shared/model/sug-tipo-unidad-medida.model';

describe('Component Tests', () => {
  describe('SugTipoUnidadMedida Management Update Component', () => {
    let comp: SugTipoUnidadMedidaUpdateComponent;
    let fixture: ComponentFixture<SugTipoUnidadMedidaUpdateComponent>;
    let service: SugTipoUnidadMedidaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugTipoUnidadMedidaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SugTipoUnidadMedidaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugTipoUnidadMedidaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugTipoUnidadMedidaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SugTipoUnidadMedida(123);
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
        const entity = new SugTipoUnidadMedida();
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
