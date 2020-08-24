import axios from 'axios';

import { IListPractices } from '@/shared/model/list-practices.model';

const baseApiUrl = 'api/list-practices';

export default class ListPracticesService {
  public find(id: number): Promise<IListPractices> {
    return new Promise<IListPractices>(resolve => {
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

  public create(entity: IListPractices): Promise<IListPractices> {
    return new Promise<IListPractices>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(response => reject(response));
    });
  }

  public update(entity: IListPractices): Promise<IListPractices> {
    return new Promise<IListPractices>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
