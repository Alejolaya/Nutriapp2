import { IProducto } from 'app/shared/model/producto.model';

export interface ITags {
  id?: number;
  descripcion?: string;
  productos?: IProducto[];
}

export class Tags implements ITags {
  constructor(public id?: number, public descripcion?: string, public productos?: IProducto[]) {}
}
