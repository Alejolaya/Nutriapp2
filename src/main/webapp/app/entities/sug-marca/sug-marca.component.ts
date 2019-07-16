import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISugMarca } from 'app/shared/model/sug-marca.model';
import { AccountService } from 'app/core';
import { SugMarcaService } from './sug-marca.service';

@Component({
  selector: 'jhi-sug-marca',
  templateUrl: './sug-marca.component.html'
})
export class SugMarcaComponent implements OnInit, OnDestroy {
  sugMarcas: ISugMarca[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sugMarcaService: SugMarcaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sugMarcaService
      .query()
      .pipe(
        filter((res: HttpResponse<ISugMarca[]>) => res.ok),
        map((res: HttpResponse<ISugMarca[]>) => res.body)
      )
      .subscribe(
        (res: ISugMarca[]) => {
          this.sugMarcas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSugMarcas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISugMarca) {
    return item.id;
  }

  registerChangeInSugMarcas() {
    this.eventSubscriber = this.eventManager.subscribe('sugMarcaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
