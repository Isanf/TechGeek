export interface ILevel {
  id?: number;
  title?: string;
  description?: string;
  studentId?: number;
}

export class Level implements ILevel {
  constructor(public id?: number, public title?: string, public description?: string, public studentId?: number) {}
}
