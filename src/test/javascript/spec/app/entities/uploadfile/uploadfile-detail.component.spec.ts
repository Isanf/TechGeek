import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { TechGeekTestModule } from '../../../test.module';
import { UploadfileDetailComponent } from 'app/entities/uploadfile/uploadfile-detail.component';
import { Uploadfile } from 'app/shared/model/uploadfile.model';

describe('Component Tests', () => {
  describe('Uploadfile Management Detail Component', () => {
    let comp: UploadfileDetailComponent;
    let fixture: ComponentFixture<UploadfileDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ uploadfile: new Uploadfile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TechGeekTestModule],
        declarations: [UploadfileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UploadfileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UploadfileDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load uploadfile on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.uploadfile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
