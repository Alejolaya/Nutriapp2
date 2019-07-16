import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISugMarca } from 'app/shared/model/sug-marca.model';

@Component({
  selector: 'jhi-sug-marca-detail',
  templateUrl: './sug-marca-detail.component.html'
})
export class SugMarcaDetailComponent implements OnInit {
  sugMarca: ISugMarca;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugMarca }) => {
      this.sugMarca = sugMarca;
    });
  }

  previousState() {
    window.history.back();
  }
}
