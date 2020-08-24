import axios from 'axios';

import { ITechPartners } from '@/shared/model/tech-partners.model';
import { ITechServices } from '@/shared/model/tech-services.model';

const baseApiUrl = 'api/tech-partners';

export default class TechPartnersService {
  public find(id: number): Promise<ITechPartners> {
    return new Promise<ITechPartners>(resolve => {
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

  public create(entity: ITechPartners): Promise<ITechPartners> {
    return new Promise<ITechPartners>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ITechPartners): Promise<ITechPartners> {
    return new Promise<ITechPartners>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByCompetitorId(id: number): Promise<ITechPartners[]> {
    const url = `${baseApiUrl}/competitor/${id}`;
    return new Promise<ITechPartners[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public refresh(body: ITechPartners[]): Promise<ITechPartners[]> {
    const url = `${baseApiUrl}/competitor`;
    return new Promise<ITechPartners[]>(resolve => {
      axios.post(url, body).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
