import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TechGeekSharedModule } from 'app/shared/shared.module';
import { ProfessorComponent } from './professor.component';
import { ProfessorDetailComponent } from './professor-detail.component';
import { ProfessorUpdateComponent } from './professor-update.component';
import { ProfessorDeleteDialogComponent } from './professor-delete-dialog.component';
import { professorRoute } from './professor.route';

@NgModule({
  imports: [TechGeekSharedModule, RouterModule.forChild(professorRoute)],
  declarations: [ProfessorComponent, ProfessorDetailComponent, ProfessorUpdateComponent, ProfessorDeleteDialogComponent],
  entryComponents: [ProfessorDeleteDialogComponent]
})
export class TechGeekProfessorModule {}
