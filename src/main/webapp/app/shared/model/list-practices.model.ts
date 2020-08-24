export interface IListPractices {
  id?: number;
  value?: string;
}

export class ListPractices implements IListPractices {
  constructor(public id?: number, public value?: string) {}
}
