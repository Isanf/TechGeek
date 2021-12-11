import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TechGeekTestModule } from '../../../test.module';
import { InfluencerDetailComponent } from 'app/entities/influencer/influencer-detail.component';
import { Influencer } from 'app/shared/model/influencer.model';

describe('Component Tests', () => {
  describe('Influencer Management Detail Component', () => {
    let comp: InfluencerDetailComponent;
    let fixture: ComponentFixture<InfluencerDetailComponent>;
    const route = ({ data: of({ influencer: new Influencer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechGeekTestModule],
        declarations: [InfluencerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InfluencerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InfluencerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load influencer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.influencer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
