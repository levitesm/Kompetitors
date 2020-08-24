import axios from 'axios';

import { IListClientsProjectTypes } from '@/shared/model/list-clients-project-types.model';

const baseApiUrl = 'api/list-clients-project-types';

export default class ListClientsProjectTypesService {
  public find(id: number): Promise<IListClientsProjectTypes> {
    return new Promise<IListClientsProjectTypes>(resolve => {
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

  public create(entity: IListClientsProjectTypes): Promise<IListClientsProjectTypes> {
    return new Promise<IListClientsProjectTypes>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IListClientsProjectTypes): Promise<IListClientsProjectTypes> {
    return new Promise<IListClientsProjectTypes>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
