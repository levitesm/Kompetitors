import axios from 'axios';

import { IRegionZipList } from '@/shared/model/region-zip-list.model';

const baseApiUrl = 'api/region-zip-lists';

export default class RegionZipListService {
  public find(id: number): Promise<IRegionZipList> {
    return new Promise<IRegionZipList>(resolve => {
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

  public create(entity: IRegionZipList): Promise<IRegionZipList> {
    return new Promise<IRegionZipList>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IRegionZipList): Promise<IRegionZipList> {
    return new Promise<IRegionZipList>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
