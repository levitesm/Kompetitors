import axios from 'axios';

import { IListTools } from '@/shared/model/list-tools.model';

const baseApiUrl = 'api/list-tools';

export default class ListToolsService {
  public find(id: number): Promise<IListTools> {
    return new Promise<IListTools>(resolve => {
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

  public create(entity: IListTools): Promise<IListTools> {
    return new Promise<IListTools>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(response => reject(response));
    });
  }

  public update(entity: IListTools): Promise<IListTools> {
    return new Promise<IListTools>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
