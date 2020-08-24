export interface IEmployeeRole {
  id?: number;
  name?: string;
}

export class EmployeeRole implements IEmployeeRole {
  constructor(public id?: number, public name?: string) {}
}
