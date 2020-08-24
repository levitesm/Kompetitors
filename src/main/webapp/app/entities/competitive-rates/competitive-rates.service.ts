import axios from 'axios';

import { ICompetitiveRates, RateTypes } from '@/shared/model/competitive-rates.model';

const baseApiUrl = 'api/competitive-rates';

export default class CompetitiveRatesService {
  public find(id: number): Promise<ICompetitiveRates> {
    return new Promise<ICompetitiveRates>(resolve => {
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

  public create(entity: ICompetitiveRates): Promise<ICompetitiveRates> {
    return new Promise<ICompetitiveRates>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(competitorId: number, type: RateTypes, value: number): Promise<ICompetitiveRates> {
    return new Promise<ICompetitiveRates>(resolve => {
      axios.put(`${baseApiUrl}/${competitorId}/${type}/${value}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public getMaximalTotalRate(): Promise<number> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + '/maximum').then(function(res) {
        resolve(res.data);
      });
    });
  }
}
