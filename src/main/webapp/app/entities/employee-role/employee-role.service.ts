import axios from 'axios';

import { IEmployeeRole } from '@/shared/model/employee-role.model';

const baseApiUrl = 'api/employee-roles';

export default class EmployeeRoleService {
  public find(id: number): Promise<IEmployeeRole> {
    return new Promise<IEmployeeRole>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl).then(function(res) {
        resolve(res.data);
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

  public create(entity: IEmployeeRole): Promise<IEmployeeRole> {
    return new Promise<IEmployeeRole>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IEmployeeRole): Promise<IEmployeeRole> {
    return new Promise<IEmployeeRole>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
