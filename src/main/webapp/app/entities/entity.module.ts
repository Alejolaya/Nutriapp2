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
      },
      {
        path: 'sug-nutrientes-adicionales',
        loadChildren: './sug-nutrientes-adicionales/sug-nutrientes-adicionales.module#NutriappSugNutrientesAdicionalesModule'
      },
      {
        path: 'sug-tags',
        loadChildren: './sug-tags/sug-tags.module#NutriappSugTagsModule'
      },
      {
        path: 'sug-unidad-medida',
        loadChildren: './sug-unidad-medida/sug-unidad-medida.module#NutriappSugUnidadMedidaModule'
      },
      {
        path: 'sug-tipo-unidad-medida',
        loadChildren: './sug-tipo-unidad-medida/sug-tipo-unidad-medida.module#NutriappSugTipoUnidadMedidaModule'
      },
      {
        path: 'sug-marca',
        loadChildren: './sug-marca/sug-marca.module#NutriappSugMarcaModule'
      },
      {
        path: 'sug-categoria',
        loadChildren: './sug-categoria/sug-categoria.module#NutriappSugCategoriaModule'
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
