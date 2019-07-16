export interface ISugTipoUnidadMedida {
  id?: number;
  nombre?: string;
}

export class SugTipoUnidadMedida implements ISugTipoUnidadMedida {
  constructor(public id?: number, public nombre?: string) {}
}
