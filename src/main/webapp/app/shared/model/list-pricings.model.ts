export interface IListPricings {
  id?: number;
  value?: string;
}

export class ListPricings implements IListPricings {
  constructor(public id?: number, public value?: string) {}
}
