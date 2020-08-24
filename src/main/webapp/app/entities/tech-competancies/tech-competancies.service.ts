import axios from 'axios';

import { ITechCompetancies } from '@/shared/model/tech-competancies.model';

const baseApiUrl = 'api/tech-competancies';

export default class TechCompetanciesService {
  public find(id: number): Promise<ITechCompetancies> {
    return new Promise<ITechCompetancies>(resolve => {
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

  public create(entity: ITechCompetancies): Promise<ITechCompetancies> {
    return new Promise<ITechCompetancies>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ITechCompetancies): Promise<ITechCompetancies> {
    return new Promise<ITechCompetancies>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByCompetitorId(id: number): Promise<ITechCompetancies[]> {
    const url = `${baseApiUrl}/competitor/${id}`;
    return new Promise<ITechCompetancies[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public refresh(body: ITechCompetancies[]): Promise<ITechCompetancies[]> {
    const url = `${baseApiUrl}/competitor`;
    return new Promise<ITechCompetancies[]>(resolve => {
      axios.post(url, body).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
