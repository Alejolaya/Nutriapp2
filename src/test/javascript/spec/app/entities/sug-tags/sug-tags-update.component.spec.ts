/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugTagsUpdateComponent } from 'app/entities/sug-tags/sug-tags-update.component';
import { SugTagsService } from 'app/entities/sug-tags/sug-tags.service';
import { SugTags } from 'app/shared/model/sug-tags.model';

describe('Component Tests', () => {
  describe('SugTags Management Update Component', () => {
    let comp: SugTagsUpdateComponent;
    let fixture: ComponentFixture<SugTagsUpdateComponent>;
    let service: SugTagsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugTagsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SugTagsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugTagsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugTagsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SugTags(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SugTags();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
