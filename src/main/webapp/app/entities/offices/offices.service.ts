import axios from 'axios';

import { IOffices } from '@/shared/model/offices.model';
import { OfficeSearchDTO } from '@/shared/model/office-search.model';

const baseApiUrl = 'api/offices';

export default class OfficesService {
  public find(id: number): Promise<IOffices> {
    return new Promise<IOffices>(resolve => {
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

  public create(entity: IOffices): Promise<IOffices> {
    return new Promise<IOffices>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IOffices): Promise<IOffices> {
    return new Promise<IOffices>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public findOffices(what: string, where: string, region: string, country: string): Promise<OfficeSearchDTO[]> {
    const url = `${baseApiUrl}/find?what=${what}&where=${where}&region=${region}&country=${country}`;
    return new Promise<OfficeSearchDTO[]>((resolve, reject) => {
      axios
        .get(url)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(error => {
          reject(error);
        });
    });
  }
}
