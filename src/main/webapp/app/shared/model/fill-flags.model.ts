export class FillFlags {
  constructor(
    public id?: number,
    public legal?: number,
    public finance?: number,
    public clients?: number,
    public technologies?: number,
    public hr?: number,
    public pr?: number
  ) {
    this.id = id || 0;
    this.legal = legal || 0;
    this.finance = finance || 0;
    this.clients = clients || 1;
    this.technologies = technologies || 0;
    this.hr = hr || 2;
    this.pr = pr || 0;
  }
}
