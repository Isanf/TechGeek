import { Moment } from 'moment';

export interface ILive {
  id?: number;
  name?: string;
  description?: string;
  source?: string;
  startDate?: Moment;
  endDate?: Moment;
  premium?: boolean;
  moduleId?: number;
}

export class Live implements ILive {
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
