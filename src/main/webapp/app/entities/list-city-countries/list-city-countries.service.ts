import axios from 'axios';

import { IListCityCountries } from '@/shared/model/list-city-countries.model';

const baseApiUrl = 'api/list-city-countries';

export default class ListCityCountriesService {
  public find(id: number): Promise<IListCityCountries> {
    return new Promise<IListCityCountries>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl).then(function(res) {
        resolve(res);
      });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.delete(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res);
      });
    });
  }

  public create(entity: IListCityCountries): Promise<IListCityCountries> {
    return new Promise<IListCityCountries>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IListCityCountries): Promise<IListCityCountries> {
    return new Promise<IListCityCountries>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
