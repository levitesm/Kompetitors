import { ICompetitors } from '@/shared/model/competitors.model';

export interface IWorkforce {
  id?: number;
  employeeNumber?: number;
  year?: Date;
  competitor?: ICompetitors;
}

export class Workforce implements IWorkforce {
  constructor(public id?: number, public employeeNumber?: number, public year?: Date, public competitor?: ICompetitors) {}
}
