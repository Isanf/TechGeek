import { ICategory } from 'app/shared/model/category.model';

export interface IInfluencer {
  id?: number;
  firstname?: string;
  lastname?: string;
  address?: string;
  email?: string;
  phone?: string;
  categories?: ICategory[];
}

export class Influencer implements IInfluencer {
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
