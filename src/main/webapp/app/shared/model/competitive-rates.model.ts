import { ICompetitors } from '@/shared/model/competitors.model';

export interface ICompetitiveRates {
  id?: number;
  totalRate?: number;
  techRate?: number;
  financeRate?: number;
  clientsRate?: number;
  hrRate?: number;
  competitor?: ICompetitors;
}

export class CompetitiveRates implements ICompetitiveRates {
  constructor(
    public id?: number,
    public totalRate?: number,
    public techRate?: number,
    public financeRate?: number,
    public clientsRate?: number,
    public hrRate?: number,
    public competitor?: ICompetitors
  ) {
    this.totalRate = totalRate || 0;
    this.techRate = techRate || 0;
    this.financeRate = financeRate || 0;
    this.clientsRate = clientsRate || 0;
    this.hrRate = hrRate || 0;
  }
}

export enum RateTypes {
  TECHNICAL = 'tech',
  FINANCIAL = 'fin',
  HR = 'hr',
  CLIENTS = 'client'
}
