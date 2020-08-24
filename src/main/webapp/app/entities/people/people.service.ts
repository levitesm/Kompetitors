import axios from 'axios';

import { IPeople } from '@/shared/model/people.model';

const baseApiUrl = 'api/people';

export default class PeopleService {
  public find(id: number): Promise<IPeople> {
    return new Promise<IPeople>(resolve => {
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

  public create(entity: IPeople): Promise<IPeople> {
    return new Promise<IPeople>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IPeople): Promise<IPeople> {
    return new Promise<IPeople>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByCompetitorIdAndTitle(id: number, title: string): Promise<IPeople[]> {
    const url = `${baseApiUrl}/competitor/${id}?title=${title}`;
    return new Promise<IPeople[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByOfficeAndTitle(id: number, title: string): Promise<IPeople[]> {
    const url = `${baseApiUrl}/office/${id}?title=${title}`;
    return new Promise<IPeople[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
