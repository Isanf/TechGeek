import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TechGeekTestModule } from '../../../test.module';
import { LiveUpdateComponent } from 'app/entities/live/live-update.component';
import { LiveService } from 'app/entities/live/live.service';
import { Live } from 'app/shared/model/live.model';

describe('Component Tests', () => {
  describe('Live Management Update Component', () => {
    let comp: LiveUpdateComponent;
    let fixture: ComponentFixture<LiveUpdateComponent>;
    let service: LiveService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechGeekTestModule],
        declarations: [LiveUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LiveUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LiveUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LiveService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Live(123);
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
        const entity = new Live();
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
