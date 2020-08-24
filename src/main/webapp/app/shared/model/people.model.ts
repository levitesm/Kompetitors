import { Competitors, ICompetitors } from '@/shared/model/competitors.model';

export interface IPeople {
  id?: number;
  fName?: string;
  lName?: string;
  title?: string;
  linkedPage?: string;
  isKey?: boolean;
  specificOffice?: number;
  competitors?: ICompetitors;
}

export class People implements IPeople {
  constructor(
    public id?: number,
    public fName?: string,
    public lName?: string,
    public title?: string,
    public linkedPage?: string,
    public isKey?: boolean,
    public specificOffice?: number,
    public competitors?: ICompetitors
  ) {
    this.isKey = this.isKey || false;
    this.fName = this.fName || '';
    this.lName = this.lName || '';
    this.title = this.title || '';
    this.linkedPage = this.linkedPage || '';
    this.specificOffice = this.specificOffice || null;
    this.competitors = this.competitors || new Competitors();
  }
}
