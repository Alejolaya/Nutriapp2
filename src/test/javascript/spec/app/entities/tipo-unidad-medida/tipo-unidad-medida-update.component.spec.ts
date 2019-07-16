/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { TipoUnidadMedidaUpdateComponent } from 'app/entities/tipo-unidad-medida/tipo-unidad-medida-update.component';
import { TipoUnidadMedidaService } from 'app/entities/tipo-unidad-medida/tipo-unidad-medida.service';
import { TipoUnidadMedida } from 'app/shared/model/tipo-unidad-medida.model';

describe('Component Tests', () => {
  describe('TipoUnidadMedida Management Update Component', () => {
    let comp: TipoUnidadMedidaUpdateComponent;
    let fixture: ComponentFixture<TipoUnidadMedidaUpdateComponent>;
    let service: TipoUnidadMedidaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [TipoUnidadMedidaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoUnidadMedidaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoUnidadMedidaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoUnidadMedidaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoUnidadMedida(123);
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
        const entity = new TipoUnidadMedida();
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
