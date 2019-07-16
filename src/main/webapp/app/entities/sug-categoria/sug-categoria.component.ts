import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISugCategoria } from 'app/shared/model/sug-categoria.model';
import { AccountService } from 'app/core';
import { SugCategoriaService } from './sug-categoria.service';

@Component({
  selector: 'jhi-sug-categoria',
  templateUrl: './sug-categoria.component.html'
})
export class SugCategoriaComponent implements OnInit, OnDestroy {
  sugCategorias: ISugCategoria[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sugCategoriaService: SugCategoriaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sugCategoriaService
      .query()
      .pipe(
        filter((res: HttpResponse<ISugCategoria[]>) => res.ok),
        map((res: HttpResponse<ISugCategoria[]>) => res.body)
      )
      .subscribe(
        (res: ISugCategoria[]) => {
          this.sugCategorias = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSugCategorias();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISugCategoria) {
    return item.id;
  }

  registerChangeInSugCategorias() {
    this.eventSubscriber = this.eventManager.subscribe('sugCategoriaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
