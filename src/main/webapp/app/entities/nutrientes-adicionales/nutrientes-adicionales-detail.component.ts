import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INutrientesAdicionales } from 'app/shared/model/nutrientes-adicionales.model';

@Component({
  selector: 'jhi-nutrientes-adicionales-detail',
  templateUrl: './nutrientes-adicionales-detail.component.html'
})
export class NutrientesAdicionalesDetailComponent implements OnInit {
  nutrientesAdicionales: INutrientesAdicionales;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nutrientesAdicionales }) => {
      this.nutrientesAdicionales = nutrientesAdicionales;
    });
  }

  previousState() {
    window.history.back();
  }
}
