export interface ICategoria {
  id?: number;
  nombreCategoria?: string;
  descripcion?: string;
}

export class Categoria implements ICategoria {
  constructor(public id?: number, public nombreCategoria?: string, public descripcion?: string) {}
}
