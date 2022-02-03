import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILive } from 'app/shared/model/live.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LiveService } from './live.service';
import { LiveDeleteDialogComponent } from './live-delete-dialog.component';
import {IModule} from "app/shared/model/module.model";
import {ModuleService} from "app/entities/module/module.service";

@Component({
  selector: 'jhi-live',
  templateUrl: './live.component.html'
})
export class LiveComponent implements OnInit, OnDestroy {
  lives?: ILive[];
  livesLast?: ILive | null;
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
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
    protected liveService: LiveService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected moduleService: ModuleService
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.liveService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ILive[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );

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

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInLives();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILive): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLives(): void {
    this.eventSubscriber = this.eventManager.subscribe('liveListModification', () => this.loadPage());
  }

  delete(live: ILive): void {
    const modalRef = this.modalService.open(LiveDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.live = live;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ILive[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/live'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.lives = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
