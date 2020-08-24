import axios from 'axios';

import { IShareHolders } from '@/shared/model/share-holders.model';

const baseApiUrl = 'api/share-holders';

export default class ShareHoldersService {
  public find(id: number): Promise<IShareHolders> {
    return new Promise<IShareHolders>(resolve => {
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

  public create(entity: IShareHolders): Promise<IShareHolders> {
    return new Promise<IShareHolders>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IShareHolders): Promise<IShareHolders> {
    return new Promise<IShareHolders>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveBySiren(siren: string): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseApiUrl}/siren/${siren}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public updateBySiren(siren: string): Promise<any> {
    return new Promise<any>(resolve => {
      axios.put(`${baseApiUrl}/siren/${siren}`).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
