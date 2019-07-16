export interface ITipoUnidadMedida {
  id?: number;
  nombre?: string;
}

export class TipoUnidadMedida implements ITipoUnidadMedida {
  constructor(public id?: number, public nombre?: string) {}
}
