import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISugTipoUnidadMedida } from 'app/shared/model/sug-tipo-unidad-medida.model';
import { SugTipoUnidadMedidaService } from './sug-tipo-unidad-medida.service';

@Component({
  selector: 'jhi-sug-tipo-unidad-medida-delete-dialog',
  templateUrl: './sug-tipo-unidad-medida-delete-dialog.component.html'
})
export class SugTipoUnidadMedidaDeleteDialogComponent {
  sugTipoUnidadMedida: ISugTipoUnidadMedida;

  constructor(
    protected sugTipoUnidadMedidaService: SugTipoUnidadMedidaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sugTipoUnidadMedidaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sugTipoUnidadMedidaListModification',
        content: 'Deleted an sugTipoUnidadMedida'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sug-tipo-unidad-medida-delete-popup',
  template: ''
})
export class SugTipoUnidadMedidaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugTipoUnidadMedida }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SugTipoUnidadMedidaDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.sugTipoUnidadMedida = sugTipoUnidadMedida;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sug-tipo-unidad-medida', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sug-tipo-unidad-medida', { outlets: { popup: null } }]);
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
