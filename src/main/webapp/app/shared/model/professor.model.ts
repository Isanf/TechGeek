import { IModule } from 'app/shared/model/module.model';

export interface IProfessor {
  id?: number;
  firstname?: string;
  lastname?: string;
  address?: string;
  email?: string;
  phone?: string;
  modules?: IModule[];
}

export class Professor implements IProfessor {
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
