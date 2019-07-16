import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISugTags, SugTags } from 'app/shared/model/sug-tags.model';
import { SugTagsService } from './sug-tags.service';
import { ISugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';
import { SugerenciaProductoService } from 'app/entities/sugerencia-producto';

@Component({
  selector: 'jhi-sug-tags-update',
  templateUrl: './sug-tags-update.component.html'
})
export class SugTagsUpdateComponent implements OnInit {
  isSaving: boolean;

  sugerenciaproductos: ISugerenciaProducto[];

  editForm = this.fb.group({
    id: [],
    descripcion: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sugTagsService: SugTagsService,
    protected sugerenciaProductoService: SugerenciaProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sugTags }) => {
      this.updateForm(sugTags);
    });
    this.sugerenciaProductoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISugerenciaProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISugerenciaProducto[]>) => response.body)
      )
      .subscribe((res: ISugerenciaProducto[]) => (this.sugerenciaproductos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sugTags: ISugTags) {
    this.editForm.patchValue({
      id: sugTags.id,
      descripcion: sugTags.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sugTags = this.createFromForm();
    if (sugTags.id !== undefined) {
      this.subscribeToSaveResponse(this.sugTagsService.update(sugTags));
    } else {
      this.subscribeToSaveResponse(this.sugTagsService.create(sugTags));
    }
  }

  private createFromForm(): ISugTags {
    return {
      ...new SugTags(),
      id: this.editForm.get(['id']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISugTags>>) {
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
