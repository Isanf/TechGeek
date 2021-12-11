export interface IFramework {
  id?: number;
  title?: string;
  description?: string;
  moduleId?: number;
}

export class Framework implements IFramework {
  constructor(public id?: number, public title?: string, public description?: string, public moduleId?: number) {}
}
