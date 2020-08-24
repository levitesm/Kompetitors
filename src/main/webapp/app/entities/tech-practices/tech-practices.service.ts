import axios from 'axios';

import { ITechPractices } from '@/shared/model/tech-practices.model';

const baseApiUrl = 'api/tech-practices';

export default class TechPracticesService {
  public find(id: number): Promise<ITechPractices> {
    return new Promise<ITechPractices>(resolve => {
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

  public create(entity: ITechPractices): Promise<ITechPractices> {
    return new Promise<ITechPractices>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ITechPractices): Promise<ITechPractices> {
    return new Promise<ITechPractices>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByCompetitorId(id: number): Promise<ITechPractices[]> {
    const url = `${baseApiUrl}/competitor/${id}`;
    return new Promise<ITechPractices[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public refresh(body: ITechPractices[]): Promise<ITechPractices[]> {
    const url = `${baseApiUrl}/competitor`;
    return new Promise<ITechPractices[]>(resolve => {
      axios.post(url, body).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
