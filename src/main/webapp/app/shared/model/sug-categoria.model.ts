export interface ISugCategoria {
  id?: number;
  nombreCategoria?: string;
  descripcion?: string;
}

export class SugCategoria implements ISugCategoria {
  constructor(public id?: number, public nombreCategoria?: string, public descripcion?: string) {}
}
