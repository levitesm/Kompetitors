export enum EmployeeLevel {
  JUNIOR = 'JUNIOR',
  MIDDLE = 'MIDDLE',
  SENIOR = 'SENIOR',
  TRAINEE = 'TRAINEE'
}

export enum Currency {
  EUR = 'EUR',
  USD = 'USD',
  RUB = 'RUB',
  AUD = 'AUD'
}

export enum PaymentType {
  PER_HOUR = 'PER_HOUR',
  PER_DAY = 'PER_DAY'
}

export interface IEmployeePricing {
  id?: number;
  level?: EmployeeLevel;
  price?: number;
  currency?: Currency;
  paymentType?: PaymentType;
  modified?: Date;
  employeeRoleId?: number;
  competitorId?: number;
}

export class EmployeePricing implements IEmployeePricing {
  constructor(
    public id?: number,
    public level?: EmployeeLevel,
    public price?: number,
    public currency?: Currency,
    public paymentType?: PaymentType,
    public modified?: Date,
    public employeeRoleId?: number,
    public competitorId?: number
  ) {}
}
