import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoUnidadMedida } from 'app/shared/model/tipo-unidad-medida.model';

@Component({
  selector: 'jhi-tipo-unidad-medida-detail',
  templateUrl: './tipo-unidad-medida-detail.component.html'
})
export class TipoUnidadMedidaDetailComponent implements OnInit {
  tipoUnidadMedida: ITipoUnidadMedida;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoUnidadMedida }) => {
      this.tipoUnidadMedida = tipoUnidadMedida;
    });
  }

  previousState() {
    window.history.back();
  }
}
