import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TechGeekTestModule } from '../../../test.module';
import { InfluencerUpdateComponent } from 'app/entities/influencer/influencer-update.component';
import { InfluencerService } from 'app/entities/influencer/influencer.service';
import { Influencer } from 'app/shared/model/influencer.model';

describe('Component Tests', () => {
  describe('Influencer Management Update Component', () => {
    let comp: InfluencerUpdateComponent;
    let fixture: ComponentFixture<InfluencerUpdateComponent>;
    let service: InfluencerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechGeekTestModule],
        declarations: [InfluencerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InfluencerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InfluencerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InfluencerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Influencer(123);
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
        const entity = new Influencer();
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
