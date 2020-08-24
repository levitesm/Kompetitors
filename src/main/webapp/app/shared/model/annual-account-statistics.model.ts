export interface IAnnualAccountStatistics {
  id?: number;
  siren?: string;
  year?: number;
  code?: number;
  message?: string;
  modified?: Date;
}

export class AnnualAccountStatistics implements IAnnualAccountStatistics {
  constructor(
    public id?: number,
    public siren?: string,
    public year?: number,
    public code?: number,
    public message?: string,
    public modified?: Date
  ) {}
}
