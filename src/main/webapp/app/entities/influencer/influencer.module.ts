import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechGeekSharedModule } from 'app/shared/shared.module';
import { InfluencerComponent } from './influencer.component';
import { InfluencerDetailComponent } from './influencer-detail.component';
import { InfluencerUpdateComponent } from './influencer-update.component';
import { InfluencerDeleteDialogComponent } from './influencer-delete-dialog.component';
import { influencerRoute } from './influencer.route';

@NgModule({
  imports: [TechGeekSharedModule, RouterModule.forChild(influencerRoute)],
  declarations: [InfluencerComponent, InfluencerDetailComponent, InfluencerUpdateComponent, InfluencerDeleteDialogComponent],
  entryComponents: [InfluencerDeleteDialogComponent]
})
export class TechGeekInfluencerModule {}
