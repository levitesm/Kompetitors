import axios from 'axios';

import { IRegionList } from '@/shared/model/region-list.model';

const baseApiUrl = 'api/region-lists';

export default class RegionListService {
  public find(id: number): Promise<IRegionList> {
    return new Promise<IRegionList>(resolve => {
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

  public create(entity: IRegionList): Promise<IRegionList> {
    return new Promise<IRegionList>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IRegionList): Promise<IRegionList> {
    return new Promise<IRegionList>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
