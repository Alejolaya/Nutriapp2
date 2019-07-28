import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoUnidadMedida, TipoUnidadMedida } from 'app/shared/model/tipo-unidad-medida.model';
import { TipoUnidadMedidaService } from './tipo-unidad-medida.service';

@Component({
  selector: 'jhi-tipo-unidad-medida-update',
  templateUrl: './tipo-unidad-medida-update.component.html'
})
export class TipoUnidadMedidaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(
    protected tipoUnidadMedidaService: TipoUnidadMedidaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipoUnidadMedida }) => {
      this.updateForm(tipoUnidadMedida);
    });
  }

  updateForm(tipoUnidadMedida: ITipoUnidadMedida) {
    this.editForm.patchValue({
      id: tipoUnidadMedida.id,
      nombre: tipoUnidadMedida.nombre
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipoUnidadMedida = this.createFromForm();
    if (tipoUnidadMedida.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoUnidadMedidaService.update(tipoUnidadMedida));
    } else {
      this.subscribeToSaveResponse(this.tipoUnidadMedidaService.create(tipoUnidadMedida));
    }
  }

  private createFromForm(): ITipoUnidadMedida {
    return {
      ...new TipoUnidadMedida(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoUnidadMedida>>) {
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
