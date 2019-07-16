/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NutriappTestModule } from '../../../test.module';
import { SugTagsComponent } from 'app/entities/sug-tags/sug-tags.component';
import { SugTagsService } from 'app/entities/sug-tags/sug-tags.service';
import { SugTags } from 'app/shared/model/sug-tags.model';

describe('Component Tests', () => {
  describe('SugTags Management Component', () => {
    let comp: SugTagsComponent;
    let fixture: ComponentFixture<SugTagsComponent>;
    let service: SugTagsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugTagsComponent],
        providers: []
      })
        .overrideTemplate(SugTagsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SugTagsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SugTagsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SugTags(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sugTags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
