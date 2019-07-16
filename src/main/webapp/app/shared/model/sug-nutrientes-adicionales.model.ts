import { ISugUnidadMedida } from 'app/shared/model/sug-unidad-medida.model';
import { ISugerenciaProducto } from 'app/shared/model/sugerencia-producto.model';

export interface ISugNutrientesAdicionales {
  id?: number;
  descripcion?: string;
  valor?: string;
  unidadPorcion?: ISugUnidadMedida;
  sugerenciaProductos?: ISugerenciaProducto[];
}

export class SugNutrientesAdicionales implements ISugNutrientesAdicionales {
  constructor(
    public id?: number,
    public descripcion?: string,
    public valor?: string,
    public unidadPorcion?: ISugUnidadMedida,
    public sugerenciaProductos?: ISugerenciaProducto[]
  ) {}
}
