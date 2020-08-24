import axios from 'axios';

import { IPrInfo } from '@/shared/model/pr-info.model';

const baseApiUrl = 'api/pr-infos';

export default class PrInfoService {
  public find(id: number): Promise<IPrInfo> {
    return new Promise<IPrInfo>(resolve => {
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

  public create(entity: IPrInfo): Promise<IPrInfo> {
    return new Promise<IPrInfo>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(response => reject(response.message));
    });
  }

  public update(entity: IPrInfo): Promise<IPrInfo> {
    return new Promise<IPrInfo>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(response => reject(response.message));
    });
  }

  public findByCompetitorId(id: number): Promise<IPrInfo> {
    const url = `${baseApiUrl}/competitor/${id}`;
    return new Promise<IPrInfo>((resolve, reject) => {
      axios
        .get(url)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(response => reject(response.message));
    });
  }
}
