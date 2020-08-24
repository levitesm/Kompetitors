import axios from 'axios';

import { ITechTools } from '@/shared/model/tech-tools.model';
import { ITechCompetancies } from '@/shared/model/tech-competancies.model';

const baseApiUrl = 'api/tech-tools';

export default class TechToolsService {
  public find(id: number): Promise<ITechTools> {
    return new Promise<ITechTools>(resolve => {
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

  public create(entity: ITechTools): Promise<ITechTools> {
    return new Promise<ITechTools>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ITechTools): Promise<ITechTools> {
    return new Promise<ITechTools>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByCompetitorId(id: number): Promise<ITechTools[]> {
    const url = `${baseApiUrl}/competitor/${id}`;
    return new Promise<ITechTools[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public refresh(body: ITechTools[]): Promise<ITechTools[]> {
    const url = `${baseApiUrl}/competitor`;
    return new Promise<ITechTools[]>(resolve => {
      axios.post(url, body).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
