import { ICompetitors } from '@/shared/model/competitors.model';

export interface IDialogs {
  id?: number;
  section?: string;
  topic?: string;
  message?: string;
  author?: string;
  date?: Date;
  attachmentURL?: string;
  competitors?: ICompetitors;
}

export class DialogsModel implements IDialogs {
  constructor(
    public id?: number,
    public section?: string,
    public topic?: string,
    public message?: string,
    public author?: string,
    public date?: Date,
    public attachmentURL?: string,
    public competitors?: ICompetitors
  ) {}
}
