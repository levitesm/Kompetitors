import axios from 'axios';

import { IRepresentatives } from '@/shared/model/representatives.model';

const baseApiUrl = 'api/representatives';

export default class RepresentativesService {
  public find(id: number): Promise<IRepresentatives> {
    return new Promise<IRepresentatives>(resolve => {
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

  public create(entity: IRepresentatives): Promise<IRepresentatives> {
    return new Promise<IRepresentatives>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IRepresentatives): Promise<IRepresentatives> {
    return new Promise<IRepresentatives>(resolve => {
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
