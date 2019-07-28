import { ITipoUnidadMedida } from 'app/shared/model/tipo-unidad-medida.model';

export interface IUnidadMedida {
  id?: number;
  abreviacion?: string;
  descripcion?: string;
  valorConversion?: number;
  unidadBase?: ITipoUnidadMedida;
}

export class UnidadMedida implements IUnidadMedida {
  constructor(
    public id?: number,
    public abreviacion?: string,
    public descripcion?: string,
    public valorConversion?: number,
    public unidadBase?: ITipoUnidadMedida
  ) {}
}
