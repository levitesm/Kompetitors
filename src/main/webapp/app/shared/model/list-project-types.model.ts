export interface IListProjectTypes {
  id?: number;
  value?: string;
}

export class ListProjectTypes implements IListProjectTypes {
  constructor(public id?: number, public value?: string) {}
}
