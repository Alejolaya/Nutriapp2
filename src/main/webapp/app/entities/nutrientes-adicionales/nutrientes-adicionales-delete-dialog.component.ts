import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INutrientesAdicionales } from 'app/shared/model/nutrientes-adicionales.model';
import { NutrientesAdicionalesService } from './nutrientes-adicionales.service';

@Component({
  selector: 'jhi-nutrientes-adicionales-delete-dialog',
  templateUrl: './nutrientes-adicionales-delete-dialog.component.html'
})
export class NutrientesAdicionalesDeleteDialogComponent {
  nutrientesAdicionales: INutrientesAdicionales;

  constructor(
    protected nutrientesAdicionalesService: NutrientesAdicionalesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.nutrientesAdicionalesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'nutrientesAdicionalesListModification',
        content: 'Deleted an nutrientesAdicionales'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-nutrientes-adicionales-delete-popup',
  template: ''
})
export class NutrientesAdicionalesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nutrientesAdicionales }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NutrientesAdicionalesDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.nutrientesAdicionales = nutrientesAdicionales;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/nutrientes-adicionales', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/nutrientes-adicionales', { outlets: { popup: null } }]);
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
