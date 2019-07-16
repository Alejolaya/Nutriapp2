import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISugTipoUnidadMedida } from 'app/shared/model/sug-tipo-unidad-medida.model';

@Component({
  selector: 'jhi-sug-tipo-unidad-medida-detail',
  templateUrl: './sug-tipo-unidad-medida-detail.component.html'
})
export class SugTipoUnidadMedidaDetailComponent implements OnInit {
  sugTipoUnidadMedida: ISugTipoUnidadMedida;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugTipoUnidadMedida }) => {
      this.sugTipoUnidadMedida = sugTipoUnidadMedida;
    });
  }

  previousState() {
    window.history.back();
  }
}
