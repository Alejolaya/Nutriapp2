/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NutriappTestModule } from '../../../test.module';
import { SugTagsDetailComponent } from 'app/entities/sug-tags/sug-tags-detail.component';
import { SugTags } from 'app/shared/model/sug-tags.model';

describe('Component Tests', () => {
  describe('SugTags Management Detail Component', () => {
    let comp: SugTagsDetailComponent;
    let fixture: ComponentFixture<SugTagsDetailComponent>;
    const route = ({ data: of({ sugTags: new SugTags(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NutriappTestModule],
        declarations: [SugTagsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SugTagsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SugTagsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sugTags).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
