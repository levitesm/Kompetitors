export interface IAdvancedSearch {
  empFrom?: number;
  empTo?: number;
  transparency?: null | boolean;
  privateEquity?: null | boolean;
  listed?: null | boolean;
}

export class AdvancedSearch implements IAdvancedSearch {
  constructor(
    public empFrom?: number,
    public empTo?: number,
    public transparency?: null | boolean,
    public privateEquity?: null | boolean,
    public listed?: null | boolean
  ) {
    this.transparency = null;
    this.privateEquity = null;
    this.listed = null;
  }
}
