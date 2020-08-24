import axios from 'axios';

import { IListActivities } from '@/shared/model/list-activities.model';

const baseApiUrl = 'api/list-activities';

export default class ListActivitiesService {
  public find(id: number): Promise<IListActivities> {
    return new Promise<IListActivities>(resolve => {
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

  public create(entity: IListActivities): Promise<IListActivities> {
    return new Promise<IListActivities>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IListActivities): Promise<IListActivities> {
    return new Promise<IListActivities>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
