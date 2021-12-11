import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechGeekSharedModule } from 'app/shared/shared.module';
import { SupporterComponent } from './supporter.component';
import { SupporterDetailComponent } from './supporter-detail.component';
import { SupporterUpdateComponent } from './supporter-update.component';
import { SupporterDeleteDialogComponent } from './supporter-delete-dialog.component';
import { supporterRoute } from './supporter.route';

@NgModule({
  imports: [TechGeekSharedModule, RouterModule.forChild(supporterRoute)],
  declarations: [SupporterComponent, SupporterDetailComponent, SupporterUpdateComponent, SupporterDeleteDialogComponent],
  entryComponents: [SupporterDeleteDialogComponent]
})
export class TechGeekSupporterModule {}
