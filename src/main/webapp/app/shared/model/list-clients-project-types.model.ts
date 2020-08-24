export interface IListClientsProjectTypes {
  id?: number;
  value?: string;
}

export class ListClientsProjectTypes implements IListClientsProjectTypes {
  constructor(public id?: number, public value?: string) {}
}
