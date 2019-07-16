import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUnidadMedida, UnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from './unidad-medida.service';
import { ITipoUnidadMedida } from 'app/shared/model/tipo-unidad-medida.model';
import { TipoUnidadMedidaService } from 'app/entities/tipo-unidad-medida';

@Component({
  selector: 'jhi-unidad-medida-update',
  templateUrl: './unidad-medida-update.component.html'
})
export class UnidadMedidaUpdateComponent implements OnInit {
  isSaving: boolean;

  tipounidadmedidas: ITipoUnidadMedida[];

  editForm = this.fb.group({
    id: [],
    abreviacion: [],
    descripcion: [],
    valorConversion: [],
    unidadBase: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected unidadMedidaService: UnidadMedidaService,
    protected tipoUnidadMedidaService: TipoUnidadMedidaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ unidadMedida }) => {
      this.updateForm(unidadMedida);
    });
    this.tipoUnidadMedidaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoUnidadMedida[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoUnidadMedida[]>) => response.body)
      )
      .subscribe((res: ITipoUnidadMedida[]) => (this.tipounidadmedidas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(unidadMedida: IUnidadMedida) {
    this.editForm.patchValue({
      id: unidadMedida.id,
      abreviacion: unidadMedida.abreviacion,
      descripcion: unidadMedida.descripcion,
      valorConversion: unidadMedida.valorConversion,
      unidadBase: unidadMedida.unidadBase
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const unidadMedida = this.createFromForm();
    if (unidadMedida.id !== undefined) {
      this.subscribeToSaveResponse(this.unidadMedidaService.update(unidadMedida));
    } else {
      this.subscribeToSaveResponse(this.unidadMedidaService.create(unidadMedida));
    }
  }

  private createFromForm(): IUnidadMedida {
    return {
      ...new UnidadMedida(),
      id: this.editForm.get(['id']).value,
      abreviacion: this.editForm.get(['abreviacion']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      valorConversion: this.editForm.get(['valorConversion']).value,
      unidadBase: this.editForm.get(['unidadBase']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnidadMedida>>) {
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

  trackTipoUnidadMedidaById(index: number, item: ITipoUnidadMedida) {
    return item.id;
  }
}
