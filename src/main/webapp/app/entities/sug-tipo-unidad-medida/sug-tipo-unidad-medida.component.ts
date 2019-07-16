import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISugTipoUnidadMedida } from 'app/shared/model/sug-tipo-unidad-medida.model';
import { AccountService } from 'app/core';
import { SugTipoUnidadMedidaService } from './sug-tipo-unidad-medida.service';

@Component({
  selector: 'jhi-sug-tipo-unidad-medida',
  templateUrl: './sug-tipo-unidad-medida.component.html'
})
export class SugTipoUnidadMedidaComponent implements OnInit, OnDestroy {
  sugTipoUnidadMedidas: ISugTipoUnidadMedida[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sugTipoUnidadMedidaService: SugTipoUnidadMedidaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sugTipoUnidadMedidaService
      .query()
      .pipe(
        filter((res: HttpResponse<ISugTipoUnidadMedida[]>) => res.ok),
        map((res: HttpResponse<ISugTipoUnidadMedida[]>) => res.body)
      )
      .subscribe(
        (res: ISugTipoUnidadMedida[]) => {
          this.sugTipoUnidadMedidas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSugTipoUnidadMedidas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISugTipoUnidadMedida) {
    return item.id;
  }

  registerChangeInSugTipoUnidadMedidas() {
    this.eventSubscriber = this.eventManager.subscribe('sugTipoUnidadMedidaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
