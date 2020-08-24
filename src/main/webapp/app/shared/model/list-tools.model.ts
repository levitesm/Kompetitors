export interface IListTools {
  id?: number;
  value?: string;
}

export class ListTools implements IListTools {
  constructor(public id?: number, public value?: string) {}
}
