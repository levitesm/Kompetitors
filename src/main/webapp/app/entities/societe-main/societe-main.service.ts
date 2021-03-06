import axios from 'axios';

import { ISocieteMain } from '@/shared/model/societe-main.model';

const baseApiUrl = 'api/societe-mains';

export default class SocieteMainService {
  public find(id: number): Promise<ISocieteMain> {
    return new Promise<ISocieteMain>(resolve => {
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

  public create(entity: ISocieteMain): Promise<ISocieteMain> {
    return new Promise<ISocieteMain>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ISocieteMain): Promise<ISocieteMain> {
    return new Promise<ISocieteMain>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
