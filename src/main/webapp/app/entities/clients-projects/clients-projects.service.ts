import axios from 'axios';

import { IClientsProjects } from '@/shared/model/clients-projects.model';

const baseApiUrl = 'api/clients-projects';

export default class ClientsProjectsService {
  public find(id: number): Promise<IClientsProjects> {
    return new Promise<IClientsProjects>(resolve => {
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

  public create(entity: IClientsProjects): Promise<IClientsProjects> {
    return new Promise<IClientsProjects>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IClientsProjects): Promise<IClientsProjects> {
    return new Promise<IClientsProjects>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
