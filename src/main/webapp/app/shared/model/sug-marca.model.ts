export interface ISugMarca {
  id?: number;
  nombreMarca?: string;
}

export class SugMarca implements ISugMarca {
  constructor(public id?: number, public nombreMarca?: string) {}
}
