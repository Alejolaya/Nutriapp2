import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';
import { SugerenciaProductoService } from './sugerencia-producto.service';

@Component({
  selector: 'jhi-sugerencia-producto-delete-dialog',
  templateUrl: './sugerencia-producto-delete-dialog.component.html'
})
export class SugerenciaProductoDeleteDialogComponent {
  sugerenciaProducto: ISugerenciaProducto;

  constructor(
    protected sugerenciaProductoService: SugerenciaProductoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sugerenciaProductoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sugerenciaProductoListModification',
        content: 'Deleted an sugerenciaProducto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sugerencia-producto-delete-popup',
  template: ''
})
export class SugerenciaProductoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugerenciaProducto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SugerenciaProductoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sugerenciaProducto = sugerenciaProducto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sugerencia-producto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sugerencia-producto', { outlets: { popup: null } }]);
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
