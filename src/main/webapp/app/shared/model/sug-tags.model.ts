import { ISugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';

export interface ISugTags {
  id?: number;
  descripcion?: string;
  sugerenciaProductos?: ISugerenciaProducto[];
}

export class SugTags implements ISugTags {
  constructor(public id?: number, public descripcion?: string, public sugerenciaProductos?: ISugerenciaProducto[]) {}
}
