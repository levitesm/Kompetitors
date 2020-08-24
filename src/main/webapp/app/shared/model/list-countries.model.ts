export interface IListCountries {
  id?: number;
  value?: string;
}

export class ListCountries implements IListCountries {
  constructor(public id?: number, public value?: string) {}
}
