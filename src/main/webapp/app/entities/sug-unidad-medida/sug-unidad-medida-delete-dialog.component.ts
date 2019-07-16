import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISugUnidadMedida } from 'app/shared/model/sug-unidad-medida.model';
import { SugUnidadMedidaService } from './sug-unidad-medida.service';

@Component({
  selector: 'jhi-sug-unidad-medida-delete-dialog',
  templateUrl: './sug-unidad-medida-delete-dialog.component.html'
})
export class SugUnidadMedidaDeleteDialogComponent {
  sugUnidadMedida: ISugUnidadMedida;

  constructor(
    protected sugUnidadMedidaService: SugUnidadMedidaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sugUnidadMedidaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sugUnidadMedidaListModification',
        content: 'Deleted an sugUnidadMedida'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sug-unidad-medida-delete-popup',
  template: ''
})
export class SugUnidadMedidaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugUnidadMedida }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SugUnidadMedidaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sugUnidadMedida = sugUnidadMedida;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sug-unidad-medida', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sug-unidad-medida', { outlets: { popup: null } }]);
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
