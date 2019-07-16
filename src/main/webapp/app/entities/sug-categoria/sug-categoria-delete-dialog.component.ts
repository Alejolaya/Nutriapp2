import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISugCategoria } from 'app/shared/model/sug-categoria.model';
import { SugCategoriaService } from './sug-categoria.service';

@Component({
  selector: 'jhi-sug-categoria-delete-dialog',
  templateUrl: './sug-categoria-delete-dialog.component.html'
})
export class SugCategoriaDeleteDialogComponent {
  sugCategoria: ISugCategoria;

  constructor(
    protected sugCategoriaService: SugCategoriaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sugCategoriaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sugCategoriaListModification',
        content: 'Deleted an sugCategoria'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sug-categoria-delete-popup',
  template: ''
})
export class SugCategoriaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugCategoria }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SugCategoriaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sugCategoria = sugCategoria;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sug-categoria', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sug-categoria', { outlets: { popup: null } }]);
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
