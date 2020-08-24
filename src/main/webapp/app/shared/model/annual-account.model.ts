export interface IAnnualAccount {
  id?: number;
  siren?: string;
  year?: number;
  code?: string;
  value?: number;
}

export class AnnualAccount implements IAnnualAccount {
  constructor(public id?: number, public siren?: string, public year?: number, public code?: string, public value?: number) {}
}
