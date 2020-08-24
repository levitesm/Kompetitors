import axios from 'axios';

import { IListServices } from '@/shared/model/list-services.model';

const baseApiUrl = 'api/list-services';

export default class ListServicesService {
  public find(id: number): Promise<IListServices> {
    return new Promise<IListServices>(resolve => {
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

  public create(entity: IListServices): Promise<IListServices> {
    return new Promise<IListServices>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(response => reject(response));
    });
  }

  public update(entity: IListServices): Promise<IListServices> {
    return new Promise<IListServices>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
