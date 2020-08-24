import { ICompetitors } from '@/shared/model/competitors.model';

export interface IFinance {
  id?: number;
  margin?: number;
  ebitda?: number;
  occupationRate?: number;
  revenue?: number;
  year?: number;
  competitors?: ICompetitors;
}

export class Finance implements IFinance {
  constructor(
    public id?: number,
    public margin?: number,
    public ebitda?: number,
    public occupationRate?: number,
    public revenue?: number,
    public year?: number,
    public competitors?: ICompetitors
  ) {}
}
