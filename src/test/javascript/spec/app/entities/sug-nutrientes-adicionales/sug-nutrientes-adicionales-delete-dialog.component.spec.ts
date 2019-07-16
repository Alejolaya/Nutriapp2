/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NutriappTestModule } from '../../../test.module';
import { SugNutrientesAdicionalesDeleteDialogComponent } from 'app/entities/sug-nutrientes-adicionales/sug-nutrientes-adicionales-delete-dialog.component';
import { SugNutrientesAdicionalesService } from 'app/entities/sug-nutrientes-adicionales/sug-nutrientes-adicionales.service';

describe('Component Tests', () => {
  describe('SugNutrientesAdicionales Management Delete Component', () => {
    let comp: SugNutrientesAdicionalesDeleteDialogComponent;
    let fixture: ComponentFixture<SugNutrientesAdicionalesDeleteDialogComponent>;
    let service: SugNutrientesAdicionalesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugNutrientesAdicionalesDeleteDialogComponent]
      })
        .overrideTemplate(SugNutrientesAdicionalesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SugNutrientesAdicionalesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugNutrientesAdicionalesService);
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
