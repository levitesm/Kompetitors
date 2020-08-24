import axios from 'axios';

import { IUpdatehistory } from '@/shared/model/updatehistory.model';

const baseApiUrl = 'api/updatehistories';

export default class UpdatehistoryService {
  public find(id: number): Promise<IUpdatehistory> {
    return new Promise<IUpdatehistory>(resolve => {
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

  public create(entity: IUpdatehistory): Promise<IUpdatehistory> {
    return new Promise<IUpdatehistory>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IUpdatehistory): Promise<IUpdatehistory> {
    return new Promise<IUpdatehistory>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
