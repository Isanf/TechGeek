import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TechGeekTestModule } from '../../../test.module';
import { FrameworkDetailComponent } from 'app/entities/framework/framework-detail.component';
import { Framework } from 'app/shared/model/framework.model';

describe('Component Tests', () => {
  describe('Framework Management Detail Component', () => {
    let comp: FrameworkDetailComponent;
    let fixture: ComponentFixture<FrameworkDetailComponent>;
    const route = ({ data: of({ framework: new Framework(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechGeekTestModule],
        declarations: [FrameworkDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FrameworkDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FrameworkDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load framework on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.framework).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
