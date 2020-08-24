import axios from 'axios';

import { ITechProjects } from '@/shared/model/tech-projects.model';
import { ITechPartners } from '@/shared/model/tech-partners.model';

const baseApiUrl = 'api/tech-projects';

export default class TechProjectsService {
  public find(id: number): Promise<ITechProjects> {
    return new Promise<ITechProjects>(resolve => {
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

  public create(entity: ITechProjects): Promise<ITechProjects> {
    return new Promise<ITechProjects>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ITechProjects): Promise<ITechProjects> {
    return new Promise<ITechProjects>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByCompetitorId(id: number): Promise<ITechProjects[]> {
    const url = `${baseApiUrl}/competitor/${id}`;
    return new Promise<ITechProjects[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public refresh(body: ITechProjects[]): Promise<ITechProjects[]> {
    const url = `${baseApiUrl}/competitor`;
    return new Promise<ITechProjects[]>(resolve => {
      axios.post(url, body).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
