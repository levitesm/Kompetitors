export interface IListIndustries {
  id?: number;
  value?: string;
}

export class ListIndustries implements IListIndustries {
  constructor(public id?: number, public value?: string) {}
}
