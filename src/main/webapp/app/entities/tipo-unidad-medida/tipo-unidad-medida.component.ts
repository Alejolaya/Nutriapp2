import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITipoUnidadMedida } from 'app/shared/model/tipo-unidad-medida.model';
import { AccountService } from 'app/core';
import { TipoUnidadMedidaService } from './tipo-unidad-medida.service';

@Component({
  selector: 'jhi-tipo-unidad-medida',
  templateUrl: './tipo-unidad-medida.component.html'
})
export class TipoUnidadMedidaComponent implements OnInit, OnDestroy {
  tipoUnidadMedidas: ITipoUnidadMedida[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected tipoUnidadMedidaService: TipoUnidadMedidaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.tipoUnidadMedidaService
      .query()
      .pipe(
        filter((res: HttpResponse<ITipoUnidadMedida[]>) => res.ok),
        map((res: HttpResponse<ITipoUnidadMedida[]>) => res.body)
      )
      .subscribe(
        (res: ITipoUnidadMedida[]) => {
          this.tipoUnidadMedidas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTipoUnidadMedidas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITipoUnidadMedida) {
    return item.id;
  }

  registerChangeInTipoUnidadMedidas() {
    this.eventSubscriber = this.eventManager.subscribe('tipoUnidadMedidaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
