import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';
import { AccountService } from 'app/core';
import { SugerenciaProductoService } from './sugerencia-producto.service';

@Component({
  selector: 'jhi-sugerencia-producto',
  templateUrl: './sugerencia-producto.component.html'
})
export class SugerenciaProductoComponent implements OnInit, OnDestroy {
  sugerenciaProductos: ISugerenciaProducto[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sugerenciaProductoService: SugerenciaProductoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sugerenciaProductoService
      .query()
      .pipe(
        filter((res: HttpResponse<ISugerenciaProducto[]>) => res.ok),
        map((res: HttpResponse<ISugerenciaProducto[]>) => res.body)
      )
      .subscribe(
        (res: ISugerenciaProducto[]) => {
          this.sugerenciaProductos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSugerenciaProductos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISugerenciaProducto) {
    return item.id;
  }

  registerChangeInSugerenciaProductos() {
    this.eventSubscriber = this.eventManager.subscribe('sugerenciaProductoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
