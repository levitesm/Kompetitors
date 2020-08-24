import axios from 'axios';

import { IListPricings } from '@/shared/model/list-pricings.model';

const baseApiUrl = 'api/list-pricings';

export default class ListPricingsService {
  public find(id: number): Promise<IListPricings> {
    return new Promise<IListPricings>(resolve => {
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

  public create(entity: IListPricings): Promise<IListPricings> {
    return new Promise<IListPricings>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IListPricings): Promise<IListPricings> {
    return new Promise<IListPricings>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
