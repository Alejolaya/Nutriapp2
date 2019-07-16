import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISugUnidadMedida } from 'app/shared/model/sug-unidad-medida.model';

@Component({
  selector: 'jhi-sug-unidad-medida-detail',
  templateUrl: './sug-unidad-medida-detail.component.html'
})
export class SugUnidadMedidaDetailComponent implements OnInit {
  sugUnidadMedida: ISugUnidadMedida;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugUnidadMedida }) => {
      this.sugUnidadMedida = sugUnidadMedida;
    });
  }

  previousState() {
    window.history.back();
  }
}
