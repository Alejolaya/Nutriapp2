/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NutriappTestModule } from '../../../test.module';
import { NutrientesAdicionalesDeleteDialogComponent } from 'app/entities/nutrientes-adicionales/nutrientes-adicionales-delete-dialog.component';
import { NutrientesAdicionalesService } from 'app/entities/nutrientes-adicionales/nutrientes-adicionales.service';

describe('Component Tests', () => {
  describe('NutrientesAdicionales Management Delete Component', () => {
    let comp: NutrientesAdicionalesDeleteDialogComponent;
    let fixture: ComponentFixture<NutrientesAdicionalesDeleteDialogComponent>;
    let service: NutrientesAdicionalesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [NutrientesAdicionalesDeleteDialogComponent]
      })
        .overrideTemplate(NutrientesAdicionalesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NutrientesAdicionalesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NutrientesAdicionalesService);
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
