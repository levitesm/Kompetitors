export interface IEmployeeSalaries {
  id?: number;
  seniority?: string;
  salary?: number;
  updateDate?: Date;
  updatedBy?: string;
  competitorId?: number;
}

export class EmployeeSalaries implements IEmployeeSalaries {
  constructor(
    public id?: number,
    public seniority?: string,
    public salary?: number,
    public updateDate?: Date,
    public updatedBy?: string,
    public competitor?: number
  ) {}
}
