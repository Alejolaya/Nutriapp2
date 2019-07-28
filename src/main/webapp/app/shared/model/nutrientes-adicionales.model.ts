import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface INutrientesAdicionales {
  id?: number;
  descripcion?: string;
  valor?: string;
  unidadPorcion?: IUnidadMedida;
  productos?: IProducto[];
}

export class NutrientesAdicionales implements INutrientesAdicionales {
  constructor(
    public id?: number,
    public descripcion?: string,
    public valor?: string,
    public unidadPorcion?: IUnidadMedida,
    public productos?: IProducto[]
  ) {}
}
