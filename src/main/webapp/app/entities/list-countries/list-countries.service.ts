import axios from 'axios';

import { IListCountries } from '@/shared/model/list-countries.model';

const baseApiUrl = 'api/list-countries';

export default class ListCountriesService {
  public find(id: number): Promise<IListCountries> {
    return new Promise<IListCountries>(resolve => {
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

  public create(entity: IListCountries): Promise<IListCountries> {
    return new Promise<IListCountries>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IListCountries): Promise<IListCountries> {
    return new Promise<IListCountries>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
