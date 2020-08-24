import axios from 'axios';

import { IEmployeePricing } from '@/shared/model/employee-pricing.model';

const baseApiUrl = 'api/employee-pricings';

export default class EmployeePricingService {
  public find(id: number): Promise<IEmployeePricing> {
    return new Promise<IEmployeePricing>(resolve => {
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

  public create(entity: IEmployeePricing): Promise<IEmployeePricing> {
    return new Promise<IEmployeePricing>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IEmployeePricing): Promise<IEmployeePricing> {
    return new Promise<IEmployeePricing>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  findByCompetitorId(id: string): Promise<IEmployeePricing[]> {
    const url = `${baseApiUrl}/competitor/${id}`;
    return new Promise<any>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
