import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';

@Component({
  selector: 'jhi-sugerencia-producto-detail',
  templateUrl: './sugerencia-producto-detail.component.html'
})
export class SugerenciaProductoDetailComponent implements OnInit {
  sugerenciaProducto: ISugerenciaProducto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sugerenciaProducto }) => {
      this.sugerenciaProducto = sugerenciaProducto;
    });
  }

  previousState() {
    window.history.back();
  }
}
