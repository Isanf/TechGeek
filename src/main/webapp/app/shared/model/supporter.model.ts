import { ICategory } from 'app/shared/model/category.model';

export interface ISupporter {
  id?: number;
  firstname?: string;
  lastname?: string;
  address?: string;
  email?: string;
  phone?: string;
  categories?: ICategory[];
}

export class Supporter implements ISupporter {
  constructor(
    public id?: number,
    public firstname?: string,
    public lastname?: string,
    public address?: string,
    public email?: string,
    public phone?: string,
    public categories?: ICategory[]
  ) {}
}
