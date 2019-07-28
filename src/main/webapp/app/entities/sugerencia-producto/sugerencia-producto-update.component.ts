import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ISugerenciaProducto, SugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';
import { SugerenciaProductoService } from './sugerencia-producto.service';

@Component({
  selector: 'jhi-sugerencia-producto-update',
  templateUrl: './sugerencia-producto-update.component.html'
})
export class SugerenciaProductoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombreAlimento: [],
    tamanoPorcion: [],
    medidaCasera: [],
    valorEnergetico: [],
    caloriasGrasa: [],
    grasaTotal: [],
    grasaSaturada: [],
    grasaInsaturada: [],
    grasaTrans: [],
    colesterol: [],
    sodio: [],
    carbohidrato: [],
    fibraDietaria: [],
    fibraInsoluble: [],
    fibraSoluble: [],
    azucares: [],
    proteina: [],
    vitaminaA: [],
    vitaminaC: [],
    calcio: [],
    hierro: [],
    gluten: [],
    azucar: [],
    integral: [],
    fechaCreacion: [],
    fechaUltimaModificacion: [],
    estadoActivo: [],
    codigoDeBarras: [],
    imagen: [],
    observaciones: [],
    estadoSugerencia: [],
    tags: [],
    nombreMarca: [],
    nombreCategoria: [],
    descCategoria: [],
    nutrientesAdicionales: [],
    descUnidadMedida: [],
    valorUnidadMedida: []
  });

  constructor(
    protected sugerenciaProductoService: SugerenciaProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sugerenciaProducto }) => {
      this.updateForm(sugerenciaProducto);
    });
  }

  updateForm(sugerenciaProducto: ISugerenciaProducto) {
    this.editForm.patchValue({
      id: sugerenciaProducto.id,
      nombreAlimento: sugerenciaProducto.nombreAlimento,
      tamanoPorcion: sugerenciaProducto.tamanoPorcion,
      medidaCasera: sugerenciaProducto.medidaCasera,
      valorEnergetico: sugerenciaProducto.valorEnergetico,
      caloriasGrasa: sugerenciaProducto.caloriasGrasa,
      grasaTotal: sugerenciaProducto.grasaTotal,
      grasaSaturada: sugerenciaProducto.grasaSaturada,
      grasaInsaturada: sugerenciaProducto.grasaInsaturada,
      grasaTrans: sugerenciaProducto.grasaTrans,
      colesterol: sugerenciaProducto.colesterol,
      sodio: sugerenciaProducto.sodio,
      carbohidrato: sugerenciaProducto.carbohidrato,
      fibraDietaria: sugerenciaProducto.fibraDietaria,
      fibraInsoluble: sugerenciaProducto.fibraInsoluble,
      fibraSoluble: sugerenciaProducto.fibraSoluble,
      azucares: sugerenciaProducto.azucares,
      proteina: sugerenciaProducto.proteina,
      vitaminaA: sugerenciaProducto.vitaminaA,
      vitaminaC: sugerenciaProducto.vitaminaC,
      calcio: sugerenciaProducto.calcio,
      hierro: sugerenciaProducto.hierro,
      gluten: sugerenciaProducto.gluten,
      azucar: sugerenciaProducto.azucar,
      integral: sugerenciaProducto.integral,
      fechaCreacion: sugerenciaProducto.fechaCreacion != null ? sugerenciaProducto.fechaCreacion.format(DATE_TIME_FORMAT) : null,
      fechaUltimaModificacion:
        sugerenciaProducto.fechaUltimaModificacion != null ? sugerenciaProducto.fechaUltimaModificacion.format(DATE_TIME_FORMAT) : null,
      estadoActivo: sugerenciaProducto.estadoActivo,
      codigoDeBarras: sugerenciaProducto.codigoDeBarras,
      imagen: sugerenciaProducto.imagen,
      observaciones: sugerenciaProducto.observaciones,
      estadoSugerencia: sugerenciaProducto.estadoSugerencia,
      tags: sugerenciaProducto.tags,
      nombreMarca: sugerenciaProducto.nombreMarca,
      nombreCategoria: sugerenciaProducto.nombreCategoria,
      descCategoria: sugerenciaProducto.descCategoria,
      nutrientesAdicionales: sugerenciaProducto.nutrientesAdicionales,
      descUnidadMedida: sugerenciaProducto.descUnidadMedida,
      valorUnidadMedida: sugerenciaProducto.valorUnidadMedida
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sugerenciaProducto = this.createFromForm();
    if (sugerenciaProducto.id !== undefined) {
      this.subscribeToSaveResponse(this.sugerenciaProductoService.update(sugerenciaProducto));
    } else {
      this.subscribeToSaveResponse(this.sugerenciaProductoService.create(sugerenciaProducto));
    }
  }

  private createFromForm(): ISugerenciaProducto {
    return {
      ...new SugerenciaProducto(),
      id: this.editForm.get(['id']).value,
      nombreAlimento: this.editForm.get(['nombreAlimento']).value,
      tamanoPorcion: this.editForm.get(['tamanoPorcion']).value,
      medidaCasera: this.editForm.get(['medidaCasera']).value,
      valorEnergetico: this.editForm.get(['valorEnergetico']).value,
      caloriasGrasa: this.editForm.get(['caloriasGrasa']).value,
      grasaTotal: this.editForm.get(['grasaTotal']).value,
      grasaSaturada: this.editForm.get(['grasaSaturada']).value,
      grasaInsaturada: this.editForm.get(['grasaInsaturada']).value,
      grasaTrans: this.editForm.get(['grasaTrans']).value,
      colesterol: this.editForm.get(['colesterol']).value,
      sodio: this.editForm.get(['sodio']).value,
      carbohidrato: this.editForm.get(['carbohidrato']).value,
      fibraDietaria: this.editForm.get(['fibraDietaria']).value,
      fibraInsoluble: this.editForm.get(['fibraInsoluble']).value,
      fibraSoluble: this.editForm.get(['fibraSoluble']).value,
      azucares: this.editForm.get(['azucares']).value,
      proteina: this.editForm.get(['proteina']).value,
      vitaminaA: this.editForm.get(['vitaminaA']).value,
      vitaminaC: this.editForm.get(['vitaminaC']).value,
      calcio: this.editForm.get(['calcio']).value,
      hierro: this.editForm.get(['hierro']).value,
      gluten: this.editForm.get(['gluten']).value,
      azucar: this.editForm.get(['azucar']).value,
      integral: this.editForm.get(['integral']).value,
      fechaCreacion:
        this.editForm.get(['fechaCreacion']).value != null
          ? moment(this.editForm.get(['fechaCreacion']).value, DATE_TIME_FORMAT)
          : undefined,
      fechaUltimaModificacion:
        this.editForm.get(['fechaUltimaModificacion']).value != null
          ? moment(this.editForm.get(['fechaUltimaModificacion']).value, DATE_TIME_FORMAT)
          : undefined,
      estadoActivo: this.editForm.get(['estadoActivo']).value,
      codigoDeBarras: this.editForm.get(['codigoDeBarras']).value,
      imagen: this.editForm.get(['imagen']).value,
      observaciones: this.editForm.get(['observaciones']).value,
      estadoSugerencia: this.editForm.get(['estadoSugerencia']).value,
      tags: this.editForm.get(['tags']).value,
      nombreMarca: this.editForm.get(['nombreMarca']).value,
      nombreCategoria: this.editForm.get(['nombreCategoria']).value,
      descCategoria: this.editForm.get(['descCategoria']).value,
      nutrientesAdicionales: this.editForm.get(['nutrientesAdicionales']).value,
      descUnidadMedida: this.editForm.get(['descUnidadMedida']).value,
      valorUnidadMedida: this.editForm.get(['valorUnidadMedida']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISugerenciaProducto>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
