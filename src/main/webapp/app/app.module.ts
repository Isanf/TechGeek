import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { TechGeekSharedModule } from 'app/shared/shared.module';
import { TechGeekCoreModule } from 'app/core/core.module';
import { TechGeekAppRoutingModule } from './app-routing.module';
import { TechGeekHomeModule } from './home/home.module';
import { TechGeekEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    TechGeekSharedModule,
    TechGeekCoreModule,
    TechGeekHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    TechGeekEntityModule,
    TechGeekAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class TechGeekAppModule {}
