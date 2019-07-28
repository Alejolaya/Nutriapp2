import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INutrientesAdicionales, NutrientesAdicionales } from 'app/shared/model/nutrientes-adicionales.model';
import { NutrientesAdicionalesService } from './nutrientes-adicionales.service';
import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from 'app/entities/unidad-medida';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';

@Component({
  selector: 'jhi-nutrientes-adicionales-update',
  templateUrl: './nutrientes-adicionales-update.component.html'
})
export class NutrientesAdicionalesUpdateComponent implements OnInit {
  isSaving: boolean;

  unidadmedidas: IUnidadMedida[];

  productos: IProducto[];

  editForm = this.fb.group({
    id: [],
    descripcion: [],
    valor: [],
    unidadPorcion: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected nutrientesAdicionalesService: NutrientesAdicionalesService,
    protected unidadMedidaService: UnidadMedidaService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ nutrientesAdicionales }) => {
      this.updateForm(nutrientesAdicionales);
    });
    this.unidadMedidaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUnidadMedida[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUnidadMedida[]>) => response.body)
      )
      .subscribe((res: IUnidadMedida[]) => (this.unidadmedidas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(nutrientesAdicionales: INutrientesAdicionales) {
    this.editForm.patchValue({
      id: nutrientesAdicionales.id,
      descripcion: nutrientesAdicionales.descripcion,
      valor: nutrientesAdicionales.valor,
      unidadPorcion: nutrientesAdicionales.unidadPorcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const nutrientesAdicionales = this.createFromForm();
    if (nutrientesAdicionales.id !== undefined) {
      this.subscribeToSaveResponse(this.nutrientesAdicionalesService.update(nutrientesAdicionales));
    } else {
      this.subscribeToSaveResponse(this.nutrientesAdicionalesService.create(nutrientesAdicionales));
    }
  }

  private createFromForm(): INutrientesAdicionales {
    return {
      ...new NutrientesAdicionales(),
      id: this.editForm.get(['id']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      valor: this.editForm.get(['valor']).value,
      unidadPorcion: this.editForm.get(['unidadPorcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INutrientesAdicionales>>) {
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

  trackUnidadMedidaById(index: number, item: IUnidadMedida) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
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
