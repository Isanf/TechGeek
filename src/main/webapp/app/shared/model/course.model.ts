import { IModule } from 'app/shared/model/module.model';

export interface ICourse {
  id?: number;
  name?: string;
  description?: string;
  modules?: IModule[];
}

export class Course implements ICourse {
  constructor(public id?: number, public name?: string, public description?: string, public modules?: IModule[]) {}
}
