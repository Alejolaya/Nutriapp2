import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISugMarca, SugMarca } from 'app/shared/model/sug-marca.model';
import { SugMarcaService } from './sug-marca.service';

@Component({
  selector: 'jhi-sug-marca-update',
  templateUrl: './sug-marca-update.component.html'
})
export class SugMarcaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombreMarca: []
  });

  constructor(protected sugMarcaService: SugMarcaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sugMarca }) => {
      this.updateForm(sugMarca);
    });
  }

  updateForm(sugMarca: ISugMarca) {
    this.editForm.patchValue({
      id: sugMarca.id,
      nombreMarca: sugMarca.nombreMarca
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sugMarca = this.createFromForm();
    if (sugMarca.id !== undefined) {
      this.subscribeToSaveResponse(this.sugMarcaService.update(sugMarca));
    } else {
      this.subscribeToSaveResponse(this.sugMarcaService.create(sugMarca));
    }
  }

  private createFromForm(): ISugMarca {
    return {
      ...new SugMarca(),
      id: this.editForm.get(['id']).value,
      nombreMarca: this.editForm.get(['nombreMarca']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISugMarca>>) {
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
