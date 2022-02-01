import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TechGeekTestModule } from '../../../test.module';
import { VolunteerDetailComponent } from 'app/entities/volunteer/volunteer-detail.component';
import { Volunteer } from 'app/shared/model/volunteer.model';

describe('Component Tests', () => {
  describe('Volunteer Management Detail Component', () => {
    let comp: VolunteerDetailComponent;
    let fixture: ComponentFixture<VolunteerDetailComponent>;
    const route = ({ data: of({ volunteer: new Volunteer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechGeekTestModule],
        declarations: [VolunteerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VolunteerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VolunteerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load volunteer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.volunteer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
