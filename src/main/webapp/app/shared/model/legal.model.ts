import { Competitors, ICompetitors } from '@/shared/model/competitors.model';
import { IListOwnerships, ListOwnerships } from '@/shared/model/list-ownerships.model';
import { IListActivities, ListActivities } from '@/shared/model/list-activities.model';
import { IListPricings, ListPricings } from '@/shared/model/list-pricings.model';

export interface ILegal {
  id?: number;
  legalAddress?: string;
  siren?: string;
  greffe?: string;
  founded?: Date;
  updateDate?: Date;
  legalForm?: string;
  competitor?: ICompetitors;
  ownership?: IListOwnerships;
  activity?: IListActivities;
  pricing?: IListPricings;
}

export class Legal implements ILegal {
  constructor(
    public id?: number,
    public legalAddress?: string,
    public siren?: string,
    public greffe?: string,
    public founded?: Date,
    public updateDate?: Date,
    public legalForm?: string,
    public competitor?: ICompetitors,
    public ownership?: IListOwnerships,
    public activity?: IListActivities,
    public pricing?: IListPricings
  ) {
    this.legalAddress = this.legalAddress || '';
    this.siren = this.siren || '';
    this.greffe = this.greffe || '';
    this.founded = this.founded || new Date();
    this.updateDate = this.updateDate || new Date();
    this.legalForm = this.legalForm || '';
  }
}
