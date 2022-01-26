export interface IUploadfile {
  id?: number;
  myfileContentType?: string;
  myfile?: any;
}

export class Uploadfile implements IUploadfile {
  constructor(public id?: number, public myfileContentType?: string, public myfile?: any) {}
}
