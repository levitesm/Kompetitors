import axios from 'axios';

import { IListProjectTypes } from '@/shared/model/list-project-types.model';

const baseApiUrl = 'api/list-project-types';

export default class ListProjectTypesService {
  public find(id: number): Promise<IListProjectTypes> {
    return new Promise<IListProjectTypes>(resolve => {
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

  public create(entity: IListProjectTypes): Promise<IListProjectTypes> {
    return new Promise<IListProjectTypes>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(response => reject(response));
    });
  }

  public update(entity: IListProjectTypes): Promise<IListProjectTypes> {
    return new Promise<IListProjectTypes>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
