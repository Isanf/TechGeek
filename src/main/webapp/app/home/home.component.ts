import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { HttpResponse } from '@angular/common/http';
import { ILive } from 'app/shared/model/live.model';
import { LiveService } from 'app/entities/live/live.service';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module/module.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  authSubscription?: Subscription;
  livesLast?: ILive | null;
  livesToday?: ILive | null;
  todayModule?: IModule | null;
  lastModule?: IModule | null;
  startDate?: string | undefined;
  endDate?: string | undefined;
  startDateToday?: string | undefined;
  endDateToday?: string | undefined;
  moduleId?: number;
  moduleIdToday?: number;

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    protected liveService: LiveService,
    protected moduleService: ModuleService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  // Load Data after the Page is fully loaded
  AfterViewInit(): void {
    this.liveService
      .findLast()
      .subscribe(
        (res: HttpResponse<ILive>) => (
          (this.livesLast = res.body),
          (this.startDate = res.body?.startDate?.toISOString()),
          (this.endDate = res.body?.endDate?.toISOString()),
          (this.moduleId = res.body?.moduleId),
          this.moduleService.findOne(this.moduleId).subscribe((result: HttpResponse<any>) => (this.lastModule = result.body))
        )
      );
  }
  loadData(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.liveService
      .findLast()
      .subscribe(
        (res: HttpResponse<ILive>) => (
          (this.livesLast = res.body),
          (this.startDate = res.body?.startDate?.toISOString()),
          (this.endDate = res.body?.endDate?.toISOString()),
          (this.moduleId = res.body?.moduleId),
          this.moduleService.findOne(this.moduleId).subscribe((result: HttpResponse<any>) => (this.lastModule = result.body))
        )
      );

    this.liveService
      .findToday()
      .subscribe(
        (res: HttpResponse<ILive>) => (
          (this.livesToday = res.body),
          (this.startDateToday = res.body?.startDate?.toISOString()),
          (this.endDateToday = res.body?.endDate?.toISOString()),
          (this.moduleIdToday = res.body?.moduleId),
          this.moduleService.findOne(this.moduleIdToday).subscribe((result: HttpResponse<any>) => (this.todayModule = result.body))
        )
      );
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  test(): void {
    alert(this.lastModule?.title);
  }
}
