import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TechGeekTestModule } from '../../../test.module';
import { UploadfileUpdateComponent } from 'app/entities/uploadfile/uploadfile-update.component';
import { UploadfileService } from 'app/entities/uploadfile/uploadfile.service';
import { Uploadfile } from 'app/shared/model/uploadfile.model';

describe('Component Tests', () => {
  describe('Uploadfile Management Update Component', () => {
    let comp: UploadfileUpdateComponent;
    let fixture: ComponentFixture<UploadfileUpdateComponent>;
    let service: UploadfileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechGeekTestModule],
        declarations: [UploadfileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UploadfileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UploadfileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UploadfileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Uploadfile(123);
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
        const entity = new Uploadfile();
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
