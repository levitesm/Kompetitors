import axios from 'axios';

import { IListTechPartners } from '@/shared/model/list-tech-partners.model';

const baseApiUrl = 'api/list-tech-partners';

export default class ListTechPartnersService {
  public find(id: number): Promise<IListTechPartners> {
    return new Promise<IListTechPartners>(resolve => {
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

  public create(entity: IListTechPartners): Promise<IListTechPartners> {
    return new Promise<IListTechPartners>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(response => reject(response));
    });
  }

  public update(entity: IListTechPartners): Promise<IListTechPartners> {
    return new Promise<IListTechPartners>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
