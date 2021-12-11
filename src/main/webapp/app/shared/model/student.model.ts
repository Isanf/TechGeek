import { IModule } from 'app/shared/model/module.model';

export interface IStudent {
  id?: number;
  firstname?: string;
  lastname?: string;
  address?: string;
  email?: string;
  phone?: string;
  levelId?: number;
  modules?: IModule[];
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public firstname?: string,
    public lastname?: string,
    public address?: string,
    public email?: string,
    public phone?: string,
    public levelId?: number,
    public modules?: IModule[]
  ) {}
}
