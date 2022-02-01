import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TechGeekTestModule } from '../../../test.module';
import { ProfessorUpdateComponent } from 'app/entities/professor/professor-update.component';
import { ProfessorService } from 'app/entities/professor/professor.service';
import { Professor } from 'app/shared/model/professor.model';

describe('Component Tests', () => {
  describe('Professor Management Update Component', () => {
    let comp: ProfessorUpdateComponent;
    let fixture: ComponentFixture<ProfessorUpdateComponent>;
    let service: ProfessorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechGeekTestModule],
        declarations: [ProfessorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProfessorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfessorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Professor(123);
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
        const entity = new Professor();
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
