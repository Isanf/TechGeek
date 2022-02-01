import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechGeekSharedModule } from 'app/shared/shared.module';
import { LiveComponent } from './live.component';
import { LiveDetailComponent } from './live-detail.component';
import { LiveUpdateComponent } from './live-update.component';
import { LiveDeleteDialogComponent } from './live-delete-dialog.component';
import { liveRoute } from './live.route';

@NgModule({
  imports: [TechGeekSharedModule, RouterModule.forChild(liveRoute)],
  declarations: [LiveComponent, LiveDetailComponent, LiveUpdateComponent, LiveDeleteDialogComponent],
  entryComponents: [LiveDeleteDialogComponent]
})
export class TechGeekLiveModule {}
