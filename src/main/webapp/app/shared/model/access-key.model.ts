export interface IAccessKey {
  id?: number;
  name?: string;
  description?: string;
}

export class AccessKey implements IAccessKey {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
