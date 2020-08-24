export interface IListServices {
  id?: number;
  value?: string;
}

export class ListServices implements IListServices {
  constructor(public id?: number, public value?: string) {}
}
