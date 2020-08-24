import axios from 'axios';

import { IUserGroupRights } from '@/shared/model/user-group-rights.model';

const baseApiUrl = 'api/user-group-rights';

export default class UserGroupRightsService {
  public find(id: number): Promise<IUserGroupRights> {
    return new Promise<IUserGroupRights>(resolve => {
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

  public create(entity: IUserGroupRights): Promise<IUserGroupRights> {
    return new Promise<IUserGroupRights>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IUserGroupRights): Promise<IUserGroupRights> {
    return new Promise<IUserGroupRights>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
