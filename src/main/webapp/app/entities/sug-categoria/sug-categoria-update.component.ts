import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISugCategoria, SugCategoria } from 'app/shared/model/sug-categoria.model';
import { SugCategoriaService } from './sug-categoria.service';

@Component({
  selector: 'jhi-sug-categoria-update',
  templateUrl: './sug-categoria-update.component.html'
})
export class SugCategoriaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombreCategoria: [],
    descripcion: []
  });

  constructor(protected sugCategoriaService: SugCategoriaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sugCategoria }) => {
      this.updateForm(sugCategoria);
    });
  }

  updateForm(sugCategoria: ISugCategoria) {
    this.editForm.patchValue({
      id: sugCategoria.id,
      nombreCategoria: sugCategoria.nombreCategoria,
      descripcion: sugCategoria.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sugCategoria = this.createFromForm();
    if (sugCategoria.id !== undefined) {
      this.subscribeToSaveResponse(this.sugCategoriaService.update(sugCategoria));
    } else {
      this.subscribeToSaveResponse(this.sugCategoriaService.create(sugCategoria));
    }
  }

  private createFromForm(): ISugCategoria {
    return {
      ...new SugCategoria(),
      id: this.editForm.get(['id']).value,
      nombreCategoria: this.editForm.get(['nombreCategoria']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISugCategoria>>) {
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
