import { Moment } from 'moment';

export const enum EstadoSugerencia {
  APROBADO = 'APROBADO',
  RECHAZADO = 'RECHAZADO',
  VALIDANDO = 'VALIDANDO'
}

export interface ISugerenciaProducto {
  id?: number;
  nombreAlimento?: string;
  tamanoPorcion?: number;
  medidaCasera?: number;
  valorEnergetico?: number;
  caloriasGrasa?: number;
  grasaTotal?: number;
  grasaSaturada?: number;
  grasaInsaturada?: number;
  grasaTrans?: number;
  colesterol?: number;
  sodio?: number;
  carbohidrato?: number;
  fibraDietaria?: number;
  fibraInsoluble?: number;
  fibraSoluble?: number;
  azucares?: number;
  proteina?: number;
  vitaminaA?: number;
  vitaminaC?: number;
  calcio?: number;
  hierro?: number;
  gluten?: boolean;
  azucar?: boolean;
  integral?: boolean;
  fechaCreacion?: Moment;
  fechaUltimaModificacion?: Moment;
  estadoActivo?: boolean;
  codigoDeBarras?: string;
  imagen?: string;
  observaciones?: string;
  estadoSugerencia?: EstadoSugerencia;
  tags?: string;
  nombreMarca?: string;
  nombreCategoria?: string;
  descCategoria?: string;
  nutrientesAdicionales?: string;
  descUnidadMedida?: string;
  valorUnidadMedida?: number;
}

export class SugerenciaProducto implements ISugerenciaProducto {
  constructor(
    public id?: number,
    public nombreAlimento?: string,
    public tamanoPorcion?: number,
    public medidaCasera?: number,
    public valorEnergetico?: number,
    public caloriasGrasa?: number,
    public grasaTotal?: number,
    public grasaSaturada?: number,
    public grasaInsaturada?: number,
    public grasaTrans?: number,
    public colesterol?: number,
    public sodio?: number,
    public carbohidrato?: number,
    public fibraDietaria?: number,
    public fibraInsoluble?: number,
    public fibraSoluble?: number,
    public azucares?: number,
    public proteina?: number,
    public vitaminaA?: number,
    public vitaminaC?: number,
    public calcio?: number,
    public hierro?: number,
    public gluten?: boolean,
    public azucar?: boolean,
    public integral?: boolean,
    public fechaCreacion?: Moment,
    public fechaUltimaModificacion?: Moment,
    public estadoActivo?: boolean,
    public codigoDeBarras?: string,
    public imagen?: string,
    public observaciones?: string,
    public estadoSugerencia?: EstadoSugerencia,
    public tags?: string,
    public nombreMarca?: string,
    public nombreCategoria?: string,
    public descCategoria?: string,
    public nutrientesAdicionales?: string,
    public descUnidadMedida?: string,
    public valorUnidadMedida?: number
  ) {
    this.gluten = this.gluten || false;
    this.azucar = this.azucar || false;
    this.integral = this.integral || false;
    this.estadoActivo = this.estadoActivo || false;
  }
}
