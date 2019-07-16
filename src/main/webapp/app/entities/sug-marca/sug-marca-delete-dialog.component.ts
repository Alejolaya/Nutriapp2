import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISugMarca } from 'app/shared/model/sug-marca.model';
import { SugMarcaService } from './sug-marca.service';

@Component({
  selector: 'jhi-sug-marca-delete-dialog',
  templateUrl: './sug-marca-delete-dialog.component.html'
})
export class SugMarcaDeleteDialogComponent {
  sugMarca: ISugMarca;

  constructor(protected sugMarcaService: SugMarcaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sugMarcaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sugMarcaListModification',
        content: 'Deleted an sugMarca'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sug-marca-delete-popup',
  template: ''
})
export class SugMarcaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugMarca }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SugMarcaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sugMarca = sugMarca;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sug-marca', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sug-marca', { outlets: { popup: null } }]);
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
