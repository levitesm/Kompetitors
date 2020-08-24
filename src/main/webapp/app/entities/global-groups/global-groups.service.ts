import axios from 'axios';

import { IGlobalGroups } from '@/shared/model/global-groups.model';

const baseApiUrl = 'api/global-groups';

export default class GlobalGroupsService {
  public find(id: number): Promise<IGlobalGroups> {
    return new Promise<IGlobalGroups>(resolve => {
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

  public create(entity: IGlobalGroups): Promise<IGlobalGroups> {
    return new Promise<IGlobalGroups>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IGlobalGroups): Promise<IGlobalGroups> {
    return new Promise<IGlobalGroups>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public findByWhatAndWhereAndRegion(what: string, where: string, region: string, country: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const str = `${baseApiUrl}/find/?what=${what}&where=${where}&region=${region}&country=${country}`;

      axios
        .get(str)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err.message);
        });
    });
  }
}
