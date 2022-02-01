import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TechGeekTestModule } from '../../../test.module';
import { FrameworkUpdateComponent } from 'app/entities/framework/framework-update.component';
import { FrameworkService } from 'app/entities/framework/framework.service';
import { Framework } from 'app/shared/model/framework.model';

describe('Component Tests', () => {
  describe('Framework Management Update Component', () => {
    let comp: FrameworkUpdateComponent;
    let fixture: ComponentFixture<FrameworkUpdateComponent>;
    let service: FrameworkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechGeekTestModule],
        declarations: [FrameworkUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FrameworkUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FrameworkUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FrameworkService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Framework(123);
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
        const entity = new Framework();
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
