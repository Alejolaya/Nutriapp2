import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISugNutrientesAdicionales, SugNutrientesAdicionales } from 'app/shared/model/sug-nutrientes-adicionales.model';
import { SugNutrientesAdicionalesService } from './sug-nutrientes-adicionales.service';
import { ISugUnidadMedida } from 'app/shared/model/sug-unidad-medida.model';
import { SugUnidadMedidaService } from 'app/entities/sug-unidad-medida';
import { ISugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';
import { SugerenciaProductoService } from 'app/entities/sugerencia-producto';

@Component({
  selector: 'jhi-sug-nutrientes-adicionales-update',
  templateUrl: './sug-nutrientes-adicionales-update.component.html'
})
export class SugNutrientesAdicionalesUpdateComponent implements OnInit {
  isSaving: boolean;

  sugunidadmedidas: ISugUnidadMedida[];

  sugerenciaproductos: ISugerenciaProducto[];

  editForm = this.fb.group({
    id: [],
    descripcion: [],
    valor: [],
    unidadPorcion: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sugNutrientesAdicionalesService: SugNutrientesAdicionalesService,
    protected sugUnidadMedidaService: SugUnidadMedidaService,
    protected sugerenciaProductoService: SugerenciaProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sugNutrientesAdicionales }) => {
      this.updateForm(sugNutrientesAdicionales);
    });
    this.sugUnidadMedidaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISugUnidadMedida[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISugUnidadMedida[]>) => response.body)
      )
      .subscribe((res: ISugUnidadMedida[]) => (this.sugunidadmedidas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.sugerenciaProductoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISugerenciaProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISugerenciaProducto[]>) => response.body)
      )
      .subscribe((res: ISugerenciaProducto[]) => (this.sugerenciaproductos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sugNutrientesAdicionales: ISugNutrientesAdicionales) {
    this.editForm.patchValue({
      id: sugNutrientesAdicionales.id,
      descripcion: sugNutrientesAdicionales.descripcion,
      valor: sugNutrientesAdicionales.valor,
      unidadPorcion: sugNutrientesAdicionales.unidadPorcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sugNutrientesAdicionales = this.createFromForm();
    if (sugNutrientesAdicionales.id !== undefined) {
      this.subscribeToSaveResponse(this.sugNutrientesAdicionalesService.update(sugNutrientesAdicionales));
    } else {
      this.subscribeToSaveResponse(this.sugNutrientesAdicionalesService.create(sugNutrientesAdicionales));
    }
  }

  private createFromForm(): ISugNutrientesAdicionales {
    return {
      ...new SugNutrientesAdicionales(),
      id: this.editForm.get(['id']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      valor: this.editForm.get(['valor']).value,
      unidadPorcion: this.editForm.get(['unidadPorcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISugNutrientesAdicionales>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSugUnidadMedidaById(index: number, item: ISugUnidadMedida) {
    return item.id;
  }

  trackSugerenciaProductoById(index: number, item: ISugerenciaProducto) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
