import { Moment } from 'moment';
import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { IMarca } from 'app/shared/model/marca.model';
import { ICategoria } from 'app/shared/model/categoria.model';
import { ITags } from 'app/shared/model/tags.model';
import { INutrientesAdicionales } from 'app/shared/model/nutrientes-adicionales.model';

export interface IProducto {
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
  unidadPorcion?: IUnidadMedida;
  unidadMedidaCasera?: IUnidadMedida;
  marca?: IMarca;
  categoria?: ICategoria;
  tags?: ITags[];
  nutrientesAdicionales?: INutrientesAdicionales[];
}

export class Producto implements IProducto {
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
    public unidadPorcion?: IUnidadMedida,
    public unidadMedidaCasera?: IUnidadMedida,
    public marca?: IMarca,
    public categoria?: ICategoria,
    public tags?: ITags[],
    public nutrientesAdicionales?: INutrientesAdicionales[]
  ) {
    this.gluten = this.gluten || false;
    this.azucar = this.azucar || false;
    this.integral = this.integral || false;
    this.estadoActivo = this.estadoActivo || false;
  }
}
