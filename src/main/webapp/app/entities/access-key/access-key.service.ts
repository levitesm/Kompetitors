import axios from 'axios';

import { IAccessKey } from '@/shared/model/access-key.model';

const baseApiUrl = 'api/access-keys';

export default class AccessKeyService {
  public find(id: number): Promise<IAccessKey> {
    return new Promise<IAccessKey>(resolve => {
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

  public create(entity: IAccessKey): Promise<IAccessKey> {
    return new Promise<IAccessKey>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IAccessKey): Promise<IAccessKey> {
    return new Promise<IAccessKey>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
