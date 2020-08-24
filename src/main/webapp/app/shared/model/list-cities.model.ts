import { IListCityCountries } from '@/shared/model/list-city-countries.model';

export interface IListCities {
  id?: number;
  value?: string;
  country?: IListCityCountries;
}

export class ListCities implements IListCities {
  constructor(public id?: number, public value?: string, public country?: IListCityCountries) {}
}
