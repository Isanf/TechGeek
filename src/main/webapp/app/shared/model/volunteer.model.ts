import { IModule } from 'app/shared/model/module.model';

export interface IVolunteer {
  id?: number;
  firstname?: string;
  lastname?: string;
  address?: string;
  email?: string;
  phone?: string;
  modules?: IModule[];
}

export class Volunteer implements IVolunteer {
  constructor(
    public id?: number,
    public firstname?: string,
    public lastname?: string,
    public address?: string,
    public email?: string,
    public phone?: string,
    public modules?: IModule[]
  ) {}
}
