import axios from 'axios';

import { IInfogreffe } from '@/shared/model/infogreffe.model';

const baseApiUrl = 'api/infogreffes';

export default class InfogreffeService {
  public find(id: number): Promise<IInfogreffe> {
    return new Promise<IInfogreffe>(resolve => {
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

  public create(entity: IInfogreffe): Promise<IInfogreffe> {
    return new Promise<IInfogreffe>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IInfogreffe): Promise<IInfogreffe> {
    return new Promise<IInfogreffe>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
