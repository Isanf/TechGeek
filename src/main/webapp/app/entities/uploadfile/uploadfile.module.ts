import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechGeekSharedModule } from 'app/shared/shared.module';
import { UploadfileComponent } from './uploadfile.component';
import { UploadfileDetailComponent } from './uploadfile-detail.component';
import { UploadfileUpdateComponent } from './uploadfile-update.component';
import { UploadfileDeleteDialogComponent } from './uploadfile-delete-dialog.component';
import { uploadfileRoute } from './uploadfile.route';

@NgModule({
  imports: [TechGeekSharedModule, RouterModule.forChild(uploadfileRoute)],
  declarations: [UploadfileComponent, UploadfileDetailComponent, UploadfileUpdateComponent, UploadfileDeleteDialogComponent],
  entryComponents: [UploadfileDeleteDialogComponent]
})
export class TechGeekUploadfileModule {}
