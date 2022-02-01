import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'professor',
        loadChildren: () => import('./professor/professor.module').then(m => m.TechGeekProfessorModule)
      },
      {
        path: 'volunteer',
        loadChildren: () => import('./volunteer/volunteer.module').then(m => m.TechGeekVolunteerModule)
      },
      {
        path: 'video',
        loadChildren: () => import('./video/video.module').then(m => m.TechGeekVideoModule)
      },
      {
        path: 'live',
        loadChildren: () => import('./live/live.module').then(m => m.TechGeekLiveModule)
      },
      {
        path: 'course',
        loadChildren: () => import('./course/course.module').then(m => m.TechGeekCourseModule)
      },
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.TechGeekStudentModule)
      },
      {
        path: 'supporter',
        loadChildren: () => import('./supporter/supporter.module').then(m => m.TechGeekSupporterModule)
      },
      {
        path: 'influencer',
        loadChildren: () => import('./influencer/influencer.module').then(m => m.TechGeekInfluencerModule)
      },
      {
        path: 'module',
        loadChildren: () => import('./module/module.module').then(m => m.TechGeekModuleModule)
      },
      {
        path: 'level',
        loadChildren: () => import('./level/level.module').then(m => m.TechGeekLevelModule)
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.TechGeekCategoryModule)
      },
      {
        path: 'language',
        loadChildren: () => import('./language/language.module').then(m => m.TechGeekLanguageModule)
      },
      {
        path: 'framework',
        loadChildren: () => import('./framework/framework.module').then(m => m.TechGeekFrameworkModule)
      },
      {
        path: 'uploadfile',
        loadChildren: () => import('./uploadfile/uploadfile.module').then(m => m.TechGeekUploadfileModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TechGeekEntityModule {}
