import axios from 'axios';

import { IDashboardFinance } from '@/shared/model/dashboard-finance.model';
import { IDashboardHR } from '@/shared/model/dashboard-hr.model';

const baseApiUrl = 'api/dashboard-finances';

export default class DashboardFinanceService {
  public find(id: number): Promise<IDashboardFinance> {
    return new Promise<IDashboardFinance>(resolve => {
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

  public create(entity: IDashboardFinance): Promise<IDashboardFinance> {
    return new Promise<IDashboardFinance>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IDashboardFinance): Promise<IDashboardFinance> {
    return new Promise<IDashboardFinance>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByYear(year: number): Promise<IDashboardFinance[]> {
    const url = `${baseApiUrl}/year/${year}`;
    return new Promise<IDashboardFinance[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByCompetitorId(competitorId: number): Promise<IDashboardFinance[]> {
    const url = `${baseApiUrl}/competitor/${competitorId}`;
    return new Promise<IDashboardFinance[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveHRByCompetitorId(competitorId: number): Promise<IDashboardHR> {
    const url = `api/hr-infos/dashboard-hr/competitor/${competitorId}`;
    return new Promise<IDashboardHR>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveHRAll(): Promise<IDashboardHR[]> {
    const url = `api/hr-infos/dashboard-hr`;
    return new Promise<IDashboardHR[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
