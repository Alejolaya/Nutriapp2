import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISugTipoUnidadMedida, SugTipoUnidadMedida } from 'app/shared/model/sug-tipo-unidad-medida.model';
import { SugTipoUnidadMedidaService } from './sug-tipo-unidad-medida.service';

@Component({
  selector: 'jhi-sug-tipo-unidad-medida-update',
  templateUrl: './sug-tipo-unidad-medida-update.component.html'
})
export class SugTipoUnidadMedidaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(
    protected sugTipoUnidadMedidaService: SugTipoUnidadMedidaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sugTipoUnidadMedida }) => {
      this.updateForm(sugTipoUnidadMedida);
    });
  }

  updateForm(sugTipoUnidadMedida: ISugTipoUnidadMedida) {
    this.editForm.patchValue({
      id: sugTipoUnidadMedida.id,
      nombre: sugTipoUnidadMedida.nombre
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sugTipoUnidadMedida = this.createFromForm();
    if (sugTipoUnidadMedida.id !== undefined) {
      this.subscribeToSaveResponse(this.sugTipoUnidadMedidaService.update(sugTipoUnidadMedida));
    } else {
      this.subscribeToSaveResponse(this.sugTipoUnidadMedidaService.create(sugTipoUnidadMedida));
    }
  }

  private createFromForm(): ISugTipoUnidadMedida {
    return {
      ...new SugTipoUnidadMedida(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISugTipoUnidadMedida>>) {
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
