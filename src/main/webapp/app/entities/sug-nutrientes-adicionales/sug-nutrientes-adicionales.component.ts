import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISugNutrientesAdicionales } from 'app/shared/model/sug-nutrientes-adicionales.model';
import { AccountService } from 'app/core';
import { SugNutrientesAdicionalesService } from './sug-nutrientes-adicionales.service';

@Component({
  selector: 'jhi-sug-nutrientes-adicionales',
  templateUrl: './sug-nutrientes-adicionales.component.html'
})
export class SugNutrientesAdicionalesComponent implements OnInit, OnDestroy {
  sugNutrientesAdicionales: ISugNutrientesAdicionales[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sugNutrientesAdicionalesService: SugNutrientesAdicionalesService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sugNutrientesAdicionalesService
      .query()
      .pipe(
        filter((res: HttpResponse<ISugNutrientesAdicionales[]>) => res.ok),
        map((res: HttpResponse<ISugNutrientesAdicionales[]>) => res.body)
      )
      .subscribe(
        (res: ISugNutrientesAdicionales[]) => {
          this.sugNutrientesAdicionales = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSugNutrientesAdicionales();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISugNutrientesAdicionales) {
    return item.id;
  }

  registerChangeInSugNutrientesAdicionales() {
    this.eventSubscriber = this.eventManager.subscribe('sugNutrientesAdicionalesListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
