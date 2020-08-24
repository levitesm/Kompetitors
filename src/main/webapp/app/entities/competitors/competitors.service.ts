import axios from 'axios';

import { ICompetitors } from '@/shared/model/competitors.model';
import { IGlobalGroups } from '@/shared/model/global-groups.model';
import { ILegal } from '@/shared/model/legal.model';
import { IOffices } from '@/shared/model/offices.model';
import { FillFlags } from '@/shared/model/fill-flags.model';

const baseApiUrl = 'api/competitors';

export default class CompetitorsService {
  public find(id: number): Promise<ICompetitors> {
    return new Promise<ICompetitors>(resolve => {
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
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(function(res) {
          resolve(res);
        })
        .catch(err => {
          reject(err.message);
        });
    });
  }

  public create(entity: ICompetitors): Promise<ICompetitors> {
    return new Promise<ICompetitors>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: ICompetitors): Promise<ICompetitors> {
    return new Promise<ICompetitors>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public findByWhatAndWhere(what: string, where: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const str = `${baseApiUrl}/find?what=${what}&where=${where}`;

      axios
        .get(str)
        .then(function(res) {
          resolve(res);
        })
        .catch(err => {
          reject(err.message);
        });
    });
  }

  public findByWhatAndWhereAndRegion(what: string, where: string, region: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const str = `${baseApiUrl}/find/region/?what=${what}&where=${where}&region=${region}`;

      axios
        .get(str)
        .then(function(res) {
          resolve(res);
        })
        .catch(err => {
          reject(err.message);
        });
    });
  }

  public findByGroupId(groupId: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      const str = `${baseApiUrl}/groupId/${groupId}`;

      axios
        .get(str)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err.message);
        });
    });
  }

  public getReferenceCompetitor(): Promise<ICompetitors> {
    return new Promise<ICompetitors>(resolve => {
      axios.get(`${baseApiUrl}/reference`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public createWithGroup(globalGroups: IGlobalGroups, legal: ILegal, offices: IOffices, competitor: ICompetitors): Promise<ICompetitors> {
    return new Promise<ICompetitors>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/group`, { globalGroups, legal, offices, competitor })
        .then(function(res) {
          resolve(res.data);
        })
        .catch(error => {
          reject(error.response.data.title);
        });
    });
  }

  public updateFillFlags(id: number, siren: string): Promise<FillFlags> {
    return new Promise<FillFlags>((resolve, reject) => {
      const str = `${baseApiUrl}/flags/${id}/siren/${siren}`;
      axios
        .get(str)
        .then(function(res) {
          resolve(res.data);
        })
        .catch(err => {
          reject(err.message);
        });
    });
  }
}
