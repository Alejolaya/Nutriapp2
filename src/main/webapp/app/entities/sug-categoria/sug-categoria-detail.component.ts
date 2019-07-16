import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISugCategoria } from 'app/shared/model/sug-categoria.model';

@Component({
  selector: 'jhi-sug-categoria-detail',
  templateUrl: './sug-categoria-detail.component.html'
})
export class SugCategoriaDetailComponent implements OnInit {
  sugCategoria: ISugCategoria;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugCategoria }) => {
      this.sugCategoria = sugCategoria;
    });
  }

  previousState() {
    window.history.back();
  }
}
