/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NutriappTestModule } from '../../../test.module';
import { TipoUnidadMedidaDeleteDialogComponent } from 'app/entities/tipo-unidad-medida/tipo-unidad-medida-delete-dialog.component';
import { TipoUnidadMedidaService } from 'app/entities/tipo-unidad-medida/tipo-unidad-medida.service';

describe('Component Tests', () => {
  describe('TipoUnidadMedida Management Delete Component', () => {
    let comp: TipoUnidadMedidaDeleteDialogComponent;
    let fixture: ComponentFixture<TipoUnidadMedidaDeleteDialogComponent>;
    let service: TipoUnidadMedidaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [TipoUnidadMedidaDeleteDialogComponent]
      })
        .overrideTemplate(TipoUnidadMedidaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoUnidadMedidaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoUnidadMedidaService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
