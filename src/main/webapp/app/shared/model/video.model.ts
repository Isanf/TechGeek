import { Moment } from 'moment';

export interface IVideo {
  id?: number;
  name?: string;
  description?: string;
  source?: string;
  startDate?: Moment;
  endDate?: Moment;
  premium?: boolean;
  moduleId?: number;
}

export class Video implements IVideo {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public source?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public premium?: boolean,
    public moduleId?: number
  ) {
    this.premium = this.premium || false;
  }
}
