export interface IListOwnerships {
  id?: number;
  value?: string;
}

export class ListOwnerships implements IListOwnerships {
  constructor(public id?: number, public value?: string) {}
}
