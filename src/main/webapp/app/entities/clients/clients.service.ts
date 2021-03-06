import axios from 'axios';

import { IClients } from '@/shared/model/clients.model';

const baseApiUrl = 'api/clients';

export default class ClientsService {
  public find(id: number): Promise<IClients> {
    return new Promise<IClients>(resolve => {
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

  public retrieveByCompetitorId(id: number): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseApiUrl}?competitorId=${id}`).then(function(res) {
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

  public create(entity: IClients): Promise<IClients> {
    return new Promise<IClients>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IClients): Promise<IClients> {
    return new Promise<IClients>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
