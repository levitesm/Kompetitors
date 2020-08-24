import axios from 'axios';

import { IListCompetancies } from '@/shared/model/list-competancies.model';

const baseApiUrl = 'api/list-competancies';

export default class ListCompetanciesService {
  public find(id: number): Promise<IListCompetancies> {
    return new Promise<IListCompetancies>(resolve => {
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

  public create(entity: IListCompetancies): Promise<IListCompetancies> {
    return new Promise<IListCompetancies>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(response => reject(response));
    });
  }

  public update(entity: IListCompetancies): Promise<IListCompetancies> {
    return new Promise<IListCompetancies>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
