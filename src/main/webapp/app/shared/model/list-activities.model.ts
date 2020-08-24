export interface IListActivities {
  id?: number;
  value?: string;
}

export class ListActivities implements IListActivities {
  constructor(public id?: number, public value?: string) {}
}
