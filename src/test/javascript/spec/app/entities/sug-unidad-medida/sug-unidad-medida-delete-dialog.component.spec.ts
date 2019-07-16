/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NutriappTestModule } from '../../../test.module';
import { SugUnidadMedidaDeleteDialogComponent } from 'app/entities/sug-unidad-medida/sug-unidad-medida-delete-dialog.component';
import { SugUnidadMedidaService } from 'app/entities/sug-unidad-medida/sug-unidad-medida.service';

describe('Component Tests', () => {
  describe('SugUnidadMedida Management Delete Component', () => {
    let comp: SugUnidadMedidaDeleteDialogComponent;
    let fixture: ComponentFixture<SugUnidadMedidaDeleteDialogComponent>;
    let service: SugUnidadMedidaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugUnidadMedidaDeleteDialogComponent]
      })
        .overrideTemplate(SugUnidadMedidaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SugUnidadMedidaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugUnidadMedidaService);
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
