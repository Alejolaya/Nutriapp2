import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISugTags } from 'app/shared/model/sug-tags.model';
import { AccountService } from 'app/core';
import { SugTagsService } from './sug-tags.service';

@Component({
  selector: 'jhi-sug-tags',
  templateUrl: './sug-tags.component.html'
})
export class SugTagsComponent implements OnInit, OnDestroy {
  sugTags: ISugTags[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sugTagsService: SugTagsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sugTagsService
      .query()
      .pipe(
        filter((res: HttpResponse<ISugTags[]>) => res.ok),
        map((res: HttpResponse<ISugTags[]>) => res.body)
      )
      .subscribe(
        (res: ISugTags[]) => {
          this.sugTags = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSugTags();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISugTags) {
    return item.id;
  }

  registerChangeInSugTags() {
    this.eventSubscriber = this.eventManager.subscribe('sugTagsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
