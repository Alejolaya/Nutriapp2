/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NutriappTestModule } from '../../../test.module';
import { SugTipoUnidadMedidaDeleteDialogComponent } from 'app/entities/sug-tipo-unidad-medida/sug-tipo-unidad-medida-delete-dialog.component';
import { SugTipoUnidadMedidaService } from 'app/entities/sug-tipo-unidad-medida/sug-tipo-unidad-medida.service';

describe('Component Tests', () => {
  describe('SugTipoUnidadMedida Management Delete Component', () => {
    let comp: SugTipoUnidadMedidaDeleteDialogComponent;
    let fixture: ComponentFixture<SugTipoUnidadMedidaDeleteDialogComponent>;
    let service: SugTipoUnidadMedidaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugTipoUnidadMedidaDeleteDialogComponent]
      })
        .overrideTemplate(SugTipoUnidadMedidaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SugTipoUnidadMedidaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugTipoUnidadMedidaService);
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
