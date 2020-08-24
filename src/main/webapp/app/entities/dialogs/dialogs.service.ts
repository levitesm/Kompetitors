import axios from 'axios';

import { IDialogs } from '@/shared/model/dialogs.model';

const baseApiUrl = 'api/dialogs';

export default class DialogsService {
  public find(id: number): Promise<IDialogs> {
    return new Promise<IDialogs>(resolve => {
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

  public create(entity: IDialogs): Promise<IDialogs> {
    return new Promise<IDialogs>(resolve => {
      axios.post(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public update(entity: IDialogs): Promise<IDialogs> {
    return new Promise<IDialogs>(resolve => {
      axios.put(`${baseApiUrl}`, entity).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public retrieveByCompIdAndSection(compId: string, section: string): Promise<any> {
    return new Promise<any>(resolve => {
      axios.get(`${baseApiUrl}/compid/${compId}/section/${section}`).then(function(res) {
        resolve(res.data);
      });
    });
  }

  public createAttachment(file: FormData, path: string): Promise<any> {
    return new Promise<any>(resolve => {
      axios.post(`${baseApiUrl}/attachments/${path}`, file).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
