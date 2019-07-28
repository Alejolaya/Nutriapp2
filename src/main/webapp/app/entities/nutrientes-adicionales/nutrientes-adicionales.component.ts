import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INutrientesAdicionales } from 'app/shared/model/nutrientes-adicionales.model';
import { AccountService } from 'app/core';
import { NutrientesAdicionalesService } from './nutrientes-adicionales.service';

@Component({
  selector: 'jhi-nutrientes-adicionales',
  templateUrl: './nutrientes-adicionales.component.html'
})
export class NutrientesAdicionalesComponent implements OnInit, OnDestroy {
  nutrientesAdicionales: INutrientesAdicionales[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected nutrientesAdicionalesService: NutrientesAdicionalesService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.nutrientesAdicionalesService
      .query()
      .pipe(
        filter((res: HttpResponse<INutrientesAdicionales[]>) => res.ok),
        map((res: HttpResponse<INutrientesAdicionales[]>) => res.body)
      )
      .subscribe(
        (res: INutrientesAdicionales[]) => {
          this.nutrientesAdicionales = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInNutrientesAdicionales();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: INutrientesAdicionales) {
    return item.id;
  }

  registerChangeInNutrientesAdicionales() {
    this.eventSubscriber = this.eventManager.subscribe('nutrientesAdicionalesListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
