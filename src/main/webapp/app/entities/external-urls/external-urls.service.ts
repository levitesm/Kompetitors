import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IExternalUrls } from '@/shared/model/external-urls.model';

const baseApiUrl = 'api/external-urls';

export default class ExternalUrlsService {
  public find(id: number): Promise<IExternalUrls> {
    return new Promise<IExternalUrls>(resolve => {
      axios.get(`${baseApiUrl}/${id}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`).then(function(res) {
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

  public create(entity: IExternalUrls): Promise<IExternalUrls> {
    return new Promise<IExternalUrls>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IExternalUrls): Promise<IExternalUrls> {
    return new Promise<IExternalUrls>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByCompetitorId(id: number): Promise<IExternalUrls> {
    const url = `${baseApiUrl}/competitor/${id}`;
    return new Promise<any>((resolve, reject) => {
      axios
        .get(url)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(error => reject(error.message));
    });
  }
}
