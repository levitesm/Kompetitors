import axios from 'axios';

import { IEmployeeType } from '@/shared/model/employee-type.model';

const baseApiUrl = 'api/employee-types';

export default class EmployeeTypeService {
  public find(id: number): Promise<IEmployeeType> {
    return new Promise<IEmployeeType>(resolve => {
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

  public create(entity: IEmployeeType): Promise<IEmployeeType> {
    return new Promise<IEmployeeType>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IEmployeeType): Promise<IEmployeeType> {
    return new Promise<IEmployeeType>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
