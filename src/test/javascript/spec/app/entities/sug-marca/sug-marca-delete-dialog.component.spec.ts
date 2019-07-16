/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NutriappTestModule } from '../../../test.module';
import { SugMarcaDeleteDialogComponent } from 'app/entities/sug-marca/sug-marca-delete-dialog.component';
import { SugMarcaService } from 'app/entities/sug-marca/sug-marca.service';

describe('Component Tests', () => {
  describe('SugMarca Management Delete Component', () => {
    let comp: SugMarcaDeleteDialogComponent;
    let fixture: ComponentFixture<SugMarcaDeleteDialogComponent>;
    let service: SugMarcaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugMarcaDeleteDialogComponent]
      })
        .overrideTemplate(SugMarcaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SugMarcaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugMarcaService);
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
