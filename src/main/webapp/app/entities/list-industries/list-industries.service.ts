import axios from 'axios';

import { IListIndustries } from '@/shared/model/list-industries.model';

const baseApiUrl = 'api/list-industries';

export default class ListIndustriesService {
  public find(id: number): Promise<IListIndustries> {
    return new Promise<IListIndustries>(resolve => {
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

  public create(entity: IListIndustries): Promise<IListIndustries> {
    return new Promise<IListIndustries>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IListIndustries): Promise<IListIndustries> {
    return new Promise<IListIndustries>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
