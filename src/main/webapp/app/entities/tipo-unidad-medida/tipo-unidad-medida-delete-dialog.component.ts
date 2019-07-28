import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoUnidadMedida } from 'app/shared/model/tipo-unidad-medida.model';
import { TipoUnidadMedidaService } from './tipo-unidad-medida.service';

@Component({
  selector: 'jhi-tipo-unidad-medida-delete-dialog',
  templateUrl: './tipo-unidad-medida-delete-dialog.component.html'
})
export class TipoUnidadMedidaDeleteDialogComponent {
  tipoUnidadMedida: ITipoUnidadMedida;

  constructor(
    protected tipoUnidadMedidaService: TipoUnidadMedidaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipoUnidadMedidaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipoUnidadMedidaListModification',
        content: 'Deleted an tipoUnidadMedida'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipo-unidad-medida-delete-popup',
  template: ''
})
export class TipoUnidadMedidaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoUnidadMedida }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipoUnidadMedidaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipoUnidadMedida = tipoUnidadMedida;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tipo-unidad-medida', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tipo-unidad-medida', { outlets: { popup: null } }]);
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
