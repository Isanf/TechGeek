import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TechGeekTestModule } from '../../../test.module';
import { LiveDetailComponent } from 'app/entities/live/live-detail.component';
import { Live } from 'app/shared/model/live.model';

describe('Component Tests', () => {
  describe('Live Management Detail Component', () => {
    let comp: LiveDetailComponent;
    let fixture: ComponentFixture<LiveDetailComponent>;
    const route = ({ data: of({ live: new Live(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechGeekTestModule],
        declarations: [LiveDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LiveDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LiveDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load live on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.live).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
