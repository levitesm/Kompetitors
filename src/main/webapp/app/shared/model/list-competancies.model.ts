export interface IListCompetancies {
  id?: number;
  value?: string;
}

export class ListCompetancies implements IListCompetancies {
  constructor(public id?: number, public value?: string) {}
}
