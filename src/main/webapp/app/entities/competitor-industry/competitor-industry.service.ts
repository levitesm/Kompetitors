import axios from 'axios';

import { ICompetitorIndustry } from '@/shared/model/competitor-industry.model';

const baseApiUrl = 'api/competitor-industries';

export default class CompetitorIndustryService {
  public find(id: number): Promise<ICompetitorIndustry> {
    return new Promise<ICompetitorIndustry>(resolve => {
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

  public create(entity: ICompetitorIndustry): Promise<ICompetitorIndustry> {
    return new Promise<ICompetitorIndustry>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ICompetitorIndustry): Promise<ICompetitorIndustry> {
    return new Promise<ICompetitorIndustry>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public getByCompetitorId(id: string): Promise<ICompetitorIndustry[]> {
    const url = `${baseApiUrl}/competitor/${id}`;
    return new Promise<ICompetitorIndustry[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public refreshCompetitorIndustries(list: ICompetitorIndustry[]): Promise<ICompetitorIndustry[]> {
    const url = `${baseApiUrl}/competitor`;
    return new Promise<ICompetitorIndustry[]>(resolve => {
      axios.post(url, list).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
