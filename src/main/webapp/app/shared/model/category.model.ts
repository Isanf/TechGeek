export interface ICategory {
  id?: number;
  title?: string;
  description?: string;
  moduleId?: number;
  supporterId?: number;
  influencerId?: number;
}

export class Category implements ICategory {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public moduleId?: number,
    public supporterId?: number,
    public influencerId?: number
  ) {}
}
