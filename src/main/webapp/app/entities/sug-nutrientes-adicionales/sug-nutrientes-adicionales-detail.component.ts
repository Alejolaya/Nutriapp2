import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISugNutrientesAdicionales } from 'app/shared/model/sug-nutrientes-adicionales.model';

@Component({
  selector: 'jhi-sug-nutrientes-adicionales-detail',
  templateUrl: './sug-nutrientes-adicionales-detail.component.html'
})
export class SugNutrientesAdicionalesDetailComponent implements OnInit {
  sugNutrientesAdicionales: ISugNutrientesAdicionales;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugNutrientesAdicionales }) => {
      this.sugNutrientesAdicionales = sugNutrientesAdicionales;
    });
  }

  previousState() {
    window.history.back();
  }
}
