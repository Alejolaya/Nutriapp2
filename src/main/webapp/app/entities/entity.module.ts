import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'producto',
        loadChildren: './producto/producto.module#NutriappProductoModule'
      },
      {
        path: 'nutrientes-adicionales',
        loadChildren: './nutrientes-adicionales/nutrientes-adicionales.module#NutriappNutrientesAdicionalesModule'
      },
      {
        path: 'tags',
        loadChildren: './tags/tags.module#NutriappTagsModule'
      },
      {
        path: 'unidad-medida',
        loadChildren: './unidad-medida/unidad-medida.module#NutriappUnidadMedidaModule'
      },
      {
        path: 'tipo-unidad-medida',
        loadChildren: './tipo-unidad-medida/tipo-unidad-medida.module#NutriappTipoUnidadMedidaModule'
      },
      {
        path: 'marca',
        loadChildren: './marca/marca.module#NutriappMarcaModule'
      },
      {
        path: 'categoria',
        loadChildren: './categoria/categoria.module#NutriappCategoriaModule'
      },
      {
        path: 'sugerencia-producto',
        loadChildren: './sugerencia-producto/sugerencia-producto.module#NutriappSugerenciaProductoModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NutriappEntityModule {}
