import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TechGeekTestModule } from '../../../test.module';
import { VolunteerUpdateComponent } from 'app/entities/volunteer/volunteer-update.component';
import { VolunteerService } from 'app/entities/volunteer/volunteer.service';
import { Volunteer } from 'app/shared/model/volunteer.model';

describe('Component Tests', () => {
  describe('Volunteer Management Update Component', () => {
    let comp: VolunteerUpdateComponent;
    let fixture: ComponentFixture<VolunteerUpdateComponent>;
    let service: VolunteerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechGeekTestModule],
        declarations: [VolunteerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VolunteerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VolunteerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VolunteerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Volunteer(123);
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
        const entity = new Volunteer();
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
