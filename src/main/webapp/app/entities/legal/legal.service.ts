import axios from 'axios';

import { ILegal } from '@/shared/model/legal.model';

const baseApiUrl = 'api/legals';

export default class LegalService {
  public find(id: number): Promise<ILegal> {
    return new Promise<ILegal>(resolve => {
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

  public create(entity: ILegal): Promise<ILegal> {
    return new Promise<ILegal>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ILegal): Promise<ILegal> {
    return new Promise<ILegal>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public refresh(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + '/refresh/' + id.toString())
        .then(function(res) {
          resolve(res);
        })
        .catch(err => {
          reject(err.message);
        });
    });
  }
}
