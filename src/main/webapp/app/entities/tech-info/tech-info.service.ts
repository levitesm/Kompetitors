import axios from 'axios';

import { ITechInfo } from '@/shared/model/tech-info.model';

const baseApiUrl = 'api/tech-infos';

export default class TechInfoService {
  public find(id: number): Promise<ITechInfo> {
    return new Promise<ITechInfo>(resolve => {
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

  public create(entity: ITechInfo): Promise<ITechInfo> {
    return new Promise<ITechInfo>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ITechInfo): Promise<ITechInfo> {
    return new Promise<ITechInfo>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByCompetitorId(id: number): Promise<ITechInfo[]> {
    const url = `${baseApiUrl}/competitor/${id}`;
    return new Promise<ITechInfo[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
