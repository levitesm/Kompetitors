import axios from 'axios';

import { ITechServices } from '@/shared/model/tech-services.model';
import { ITechTools } from '@/shared/model/tech-tools.model';

const baseApiUrl = 'api/tech-services';

export default class TechServicesService {
  public find(id: number): Promise<ITechServices> {
    return new Promise<ITechServices>(resolve => {
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

  public create(entity: ITechServices): Promise<ITechServices> {
    return new Promise<ITechServices>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ITechServices): Promise<ITechServices> {
    return new Promise<ITechServices>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByCompetitorId(id: number): Promise<ITechServices[]> {
    const url = `${baseApiUrl}/competitor/${id}`;
    return new Promise<ITechServices[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public refresh(body: ITechServices[]): Promise<ITechServices[]> {
    const url = `${baseApiUrl}/competitor`;
    return new Promise<ITechServices[]>(resolve => {
      axios.post(url, body).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
