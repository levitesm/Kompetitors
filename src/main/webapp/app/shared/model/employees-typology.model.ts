export interface IEmployeesTypology {
  id?: number;
  value?: number;
  year?: number;
  employeeTypeId?: number;
  employeeTypeName?: string;
  competitorId?: number;
}

export class EmployeesTypology implements IEmployeesTypology {
  constructor(
    public id?: number,
    public value?: number,
    public year?: number,
    public employeeTypeId?: number,
    public employeeTypeName?: string,
    public competitorId?: number
  ) {}
}
