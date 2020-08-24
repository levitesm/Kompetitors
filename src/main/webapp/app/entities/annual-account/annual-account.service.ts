import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IAnnualAccount } from '@/shared/model/annual-account.model';

const baseApiUrl = 'api/annual-accounts';

export default class AnnualAccountService {
  public find(id: number): Promise<IAnnualAccount> {
    return new Promise<IAnnualAccount>(resolve => {
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

  public create(entity: IAnnualAccount): Promise<IAnnualAccount> {
    return new Promise<IAnnualAccount>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IAnnualAccount): Promise<IAnnualAccount> {
    return new Promise<IAnnualAccount>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public getAllBySiren(siren: String): Promise<IAnnualAccount[]> {
    return new Promise<IAnnualAccount[]>(resolve => {
      axios.get(`${baseApiUrl}/siren/${siren}`).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
