import axios from 'axios';

import { IListOwnerships } from '@/shared/model/list-ownerships.model';

const baseApiUrl = 'api/list-ownerships';

export default class ListOwnershipsService {
  public find(id: number): Promise<IListOwnerships> {
    return new Promise<IListOwnerships>(resolve => {
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

  public create(entity: IListOwnerships): Promise<IListOwnerships> {
    return new Promise<IListOwnerships>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IListOwnerships): Promise<IListOwnerships> {
    return new Promise<IListOwnerships>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
