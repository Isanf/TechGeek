export interface ILanguage {
  id?: number;
  title?: string;
  description?: string;
  moduleId?: number;
}

export class Language implements ILanguage {
  constructor(public id?: number, public title?: string, public description?: string, public moduleId?: number) {}
}
