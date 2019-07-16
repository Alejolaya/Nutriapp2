import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISugTags } from 'app/shared/model/sug-tags.model';
import { SugTagsService } from './sug-tags.service';

@Component({
  selector: 'jhi-sug-tags-delete-dialog',
  templateUrl: './sug-tags-delete-dialog.component.html'
})
export class SugTagsDeleteDialogComponent {
  sugTags: ISugTags;

  constructor(protected sugTagsService: SugTagsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sugTagsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sugTagsListModification',
        content: 'Deleted an sugTags'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sug-tags-delete-popup',
  template: ''
})
export class SugTagsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugTags }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SugTagsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sugTags = sugTags;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sug-tags', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sug-tags', { outlets: { popup: null } }]);
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
