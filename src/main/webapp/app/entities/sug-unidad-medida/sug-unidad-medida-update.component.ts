import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISugUnidadMedida, SugUnidadMedida } from 'app/shared/model/sug-unidad-medida.model';
import { SugUnidadMedidaService } from './sug-unidad-medida.service';
import { ISugTipoUnidadMedida } from 'app/shared/model/sug-tipo-unidad-medida.model';
import { SugTipoUnidadMedidaService } from 'app/entities/sug-tipo-unidad-medida';

@Component({
  selector: 'jhi-sug-unidad-medida-update',
  templateUrl: './sug-unidad-medida-update.component.html'
})
export class SugUnidadMedidaUpdateComponent implements OnInit {
  isSaving: boolean;

  sugtipounidadmedidas: ISugTipoUnidadMedida[];

  editForm = this.fb.group({
    id: [],
    abreviacion: [],
    descripcion: [],
    valorConversion: [],
    unidadBase: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sugUnidadMedidaService: SugUnidadMedidaService,
    protected sugTipoUnidadMedidaService: SugTipoUnidadMedidaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sugUnidadMedida }) => {
      this.updateForm(sugUnidadMedida);
    });
    this.sugTipoUnidadMedidaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISugTipoUnidadMedida[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISugTipoUnidadMedida[]>) => response.body)
      )
      .subscribe((res: ISugTipoUnidadMedida[]) => (this.sugtipounidadmedidas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sugUnidadMedida: ISugUnidadMedida) {
    this.editForm.patchValue({
      id: sugUnidadMedida.id,
      abreviacion: sugUnidadMedida.abreviacion,
      descripcion: sugUnidadMedida.descripcion,
      valorConversion: sugUnidadMedida.valorConversion,
      unidadBase: sugUnidadMedida.unidadBase
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sugUnidadMedida = this.createFromForm();
    if (sugUnidadMedida.id !== undefined) {
      this.subscribeToSaveResponse(this.sugUnidadMedidaService.update(sugUnidadMedida));
    } else {
      this.subscribeToSaveResponse(this.sugUnidadMedidaService.create(sugUnidadMedida));
    }
  }

  private createFromForm(): ISugUnidadMedida {
    return {
      ...new SugUnidadMedida(),
      id: this.editForm.get(['id']).value,
      abreviacion: this.editForm.get(['abreviacion']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      valorConversion: this.editForm.get(['valorConversion']).value,
      unidadBase: this.editForm.get(['unidadBase']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISugUnidadMedida>>) {
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

  trackSugTipoUnidadMedidaById(index: number, item: ISugTipoUnidadMedida) {
    return item.id;
  }
}
