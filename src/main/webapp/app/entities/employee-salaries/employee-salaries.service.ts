import axios from 'axios';

import { IEmployeeSalaries } from '@/shared/model/employee-salaries.model';

const baseApiUrl = 'api/employee-salaries';

export default class EmployeeSalariesService {
  public find(id: number): Promise<IEmployeeSalaries> {
    return new Promise<IEmployeeSalaries>(resolve => {
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

  public create(entity: IEmployeeSalaries): Promise<IEmployeeSalaries> {
    return new Promise<IEmployeeSalaries>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IEmployeeSalaries): Promise<IEmployeeSalaries> {
    return new Promise<IEmployeeSalaries>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public findByCompetitorId(id: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseApiUrl}/competitor/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
