import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TechGeekTestModule } from '../../../test.module';
import { SupporterUpdateComponent } from 'app/entities/supporter/supporter-update.component';
import { SupporterService } from 'app/entities/supporter/supporter.service';
import { Supporter } from 'app/shared/model/supporter.model';

describe('Component Tests', () => {
  describe('Supporter Management Update Component', () => {
    let comp: SupporterUpdateComponent;
    let fixture: ComponentFixture<SupporterUpdateComponent>;
    let service: SupporterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechGeekTestModule],
        declarations: [SupporterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SupporterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupporterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupporterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Supporter(123);
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
        const entity = new Supporter();
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
