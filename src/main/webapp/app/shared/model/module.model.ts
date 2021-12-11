import { Moment } from 'moment';
import { IFramework } from 'app/shared/model/framework.model';
import { ILanguage } from 'app/shared/model/language.model';

export interface IModule {
  id?: number;
  title?: string;
  description?: string;
  startDate?: Moment;
  endDate?: Moment;
  categoryId?: number;
  frameworks?: IFramework[];
  languages?: ILanguage[];
  liveId?: number;
  videoId?: number;
  courseId?: number;
  professorId?: number;
  volunteerId?: number;
  studentId?: number;
}

export class Module implements IModule {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public categoryId?: number,
    public frameworks?: IFramework[],
    public languages?: ILanguage[],
    public liveId?: number,
    public videoId?: number,
    public courseId?: number,
    public professorId?: number,
    public volunteerId?: number,
    public studentId?: number
  ) {}
}
