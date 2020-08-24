import axios from 'axios';

import { IHrInfo } from '@/shared/model/hr-info.model';

const baseApiUrl = 'api/hr-infos';

export default class HrInfoService {
  public find(id: number): Promise<IHrInfo> {
    return new Promise<IHrInfo>(resolve => {
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

  public create(entity: IHrInfo): Promise<IHrInfo> {
    return new Promise<IHrInfo>(resolve => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(error => {
          throw `Fair ${error}`;
        });
    });
  }

  public update(entity: IHrInfo): Promise<IHrInfo> {
    return new Promise<IHrInfo>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
