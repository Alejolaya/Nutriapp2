import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISugNutrientesAdicionales } from 'app/shared/model/sug-nutrientes-adicionales.model';
import { SugNutrientesAdicionalesService } from './sug-nutrientes-adicionales.service';

@Component({
  selector: 'jhi-sug-nutrientes-adicionales-delete-dialog',
  templateUrl: './sug-nutrientes-adicionales-delete-dialog.component.html'
})
export class SugNutrientesAdicionalesDeleteDialogComponent {
  sugNutrientesAdicionales: ISugNutrientesAdicionales;

  constructor(
    protected sugNutrientesAdicionalesService: SugNutrientesAdicionalesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sugNutrientesAdicionalesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sugNutrientesAdicionalesListModification',
        content: 'Deleted an sugNutrientesAdicionales'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sug-nutrientes-adicionales-delete-popup',
  template: ''
})
export class SugNutrientesAdicionalesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugNutrientesAdicionales }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SugNutrientesAdicionalesDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.sugNutrientesAdicionales = sugNutrientesAdicionales;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sug-nutrientes-adicionales', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sug-nutrientes-adicionales', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
