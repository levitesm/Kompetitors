export interface IListCityCountries {
  id?: number;
  value?: string;
}

export class ListCityCountries implements IListCityCountries {
  constructor(public id?: number, public value?: string) {}
}
