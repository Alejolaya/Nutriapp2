import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISugUnidadMedida } from 'app/shared/model/sug-unidad-medida.model';
import { AccountService } from 'app/core';
import { SugUnidadMedidaService } from './sug-unidad-medida.service';

@Component({
  selector: 'jhi-sug-unidad-medida',
  templateUrl: './sug-unidad-medida.component.html'
})
export class SugUnidadMedidaComponent implements OnInit, OnDestroy {
  sugUnidadMedidas: ISugUnidadMedida[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sugUnidadMedidaService: SugUnidadMedidaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sugUnidadMedidaService
      .query()
      .pipe(
        filter((res: HttpResponse<ISugUnidadMedida[]>) => res.ok),
        map((res: HttpResponse<ISugUnidadMedida[]>) => res.body)
      )
      .subscribe(
        (res: ISugUnidadMedida[]) => {
          this.sugUnidadMedidas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSugUnidadMedidas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISugUnidadMedida) {
    return item.id;
  }

  registerChangeInSugUnidadMedidas() {
    this.eventSubscriber = this.eventManager.subscribe('sugUnidadMedidaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
