import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechGeekSharedModule } from 'app/shared/shared.module';
import { FrameworkComponent } from './framework.component';
import { FrameworkDetailComponent } from './framework-detail.component';
import { FrameworkUpdateComponent } from './framework-update.component';
import { FrameworkDeleteDialogComponent } from './framework-delete-dialog.component';
import { frameworkRoute } from './framework.route';

@NgModule({
  imports: [TechGeekSharedModule, RouterModule.forChild(frameworkRoute)],
  declarations: [FrameworkComponent, FrameworkDetailComponent, FrameworkUpdateComponent, FrameworkDeleteDialogComponent],
  entryComponents: [FrameworkDeleteDialogComponent]
})
export class TechGeekFrameworkModule {}
