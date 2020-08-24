import axios from 'axios';

import { IListCities } from '@/shared/model/list-cities.model';

const baseApiUrl = 'api/list-cities';

export default class ListCitiesService {
  public find(id: number): Promise<IListCities> {
    return new Promise<IListCities>(resolve => {
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

  public create(entity: IListCities): Promise<IListCities> {
    return new Promise<IListCities>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IListCities): Promise<IListCities> {
    return new Promise<IListCities>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
