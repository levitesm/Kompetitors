import axios from 'axios';

import { IWorkforce } from '@/shared/model/workforce.model';

const baseApiUrl = 'api/workforces';

export default class WorkforceService {
  public find(id: number): Promise<IWorkforce> {
    return new Promise<IWorkforce>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  async findByCompetitorId(competitorId: number) {
    return new Promise<IWorkforce[]>(resolve => {
      axios.get(`${baseApiUrl}/competitor/${competitorId}`).then(function(res) {
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

  public create(entity: IWorkforce): Promise<IWorkforce> {
    return new Promise<IWorkforce>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IWorkforce): Promise<IWorkforce> {
    return new Promise<IWorkforce>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
