import axios from 'axios';

import { ICapital } from '@/shared/model/capital.model';

const baseApiUrl = 'api/capitals';

export default class CapitalService {
  public find(id: number): Promise<ICapital> {
    return new Promise<ICapital>(resolve => {
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

  public create(entity: ICapital): Promise<ICapital> {
    return new Promise<ICapital>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ICapital): Promise<ICapital> {
    return new Promise<ICapital>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveBySiren(siren: string): Promise<ICapital> {
    return new Promise<ICapital>(resolve => {
      axios.get(`${baseApiUrl}/siren/${siren}`).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
