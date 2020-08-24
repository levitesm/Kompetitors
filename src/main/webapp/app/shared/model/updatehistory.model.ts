export interface IUpdatehistory {
  id?: number;
  type?: string;
  siren?: string;
  date?: Date;
  status?: string;
  responce?: string;
}

export class Updatehistory implements IUpdatehistory {
  constructor(
    public id?: number,
    public type?: string,
    public siren?: string,
    public date?: Date,
    public status?: string,
    public responce?: string
  ) {}
}
