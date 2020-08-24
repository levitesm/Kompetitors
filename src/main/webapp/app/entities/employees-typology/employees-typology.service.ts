import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { EmployeesTypology, IEmployeesTypology } from '@/shared/model/employees-typology.model';

const baseApiUrl = 'api/employees-typologies';

export default class EmployeesTypologyService {
  public find(id: number): Promise<IEmployeesTypology> {
    return new Promise<IEmployeesTypology>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
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

  public create(entity: IEmployeesTypology): Promise<IEmployeesTypology> {
    return new Promise<IEmployeesTypology>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IEmployeesTypology): Promise<IEmployeesTypology> {
    return new Promise<IEmployeesTypology>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByCompetitorId(competitorId: number): Promise<IEmployeesTypology[]> {
    const url = `${baseApiUrl}/competitor/${competitorId}`;
    return new Promise<any>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public updateAll(typologies: EmployeesTypology[]): Promise<IEmployeesTypology[]> {
    const url = `${baseApiUrl}/all`;
    return new Promise<any>((resolve, reject) => {
      axios
        .post(url, typologies)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err.message);
        });
    });
  }
}
