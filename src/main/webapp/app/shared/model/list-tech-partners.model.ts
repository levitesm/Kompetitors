export interface IListTechPartners {
  id?: number;
  value?: string;
  imageContentType?: string;
  image?: any;
}

export class ListTechPartners implements IListTechPartners {
  constructor(public id?: number, public value?: string, public imageContentType?: string, public image?: any) {}
}
