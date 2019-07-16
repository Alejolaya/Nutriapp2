import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISugTags } from 'app/shared/model/sug-tags.model';

@Component({
  selector: 'jhi-sug-tags-detail',
  templateUrl: './sug-tags-detail.component.html'
})
export class SugTagsDetailComponent implements OnInit {
  sugTags: ISugTags;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugTags }) => {
      this.sugTags = sugTags;
    });
  }

  previousState() {
    window.history.back();
  }
}
