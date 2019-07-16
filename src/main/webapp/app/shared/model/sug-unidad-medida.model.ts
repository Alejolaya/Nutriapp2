import { ISugTipoUnidadMedida } from 'app/shared/model/sug-tipo-unidad-medida.model';

export interface ISugUnidadMedida {
  id?: number;
  abreviacion?: string;
  descripcion?: string;
  valorConversion?: number;
  unidadBase?: ISugTipoUnidadMedida;
}

export class SugUnidadMedida implements ISugUnidadMedida {
  constructor(
    public id?: number,
    public abreviacion?: string,
    public descripcion?: string,
    public valorConversion?: number,
    public unidadBase?: ISugTipoUnidadMedida
  ) {}
}
