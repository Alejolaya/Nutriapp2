/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SugerenciaProductoService } from 'app/entities/sugerencia-producto/sugerencia-producto.service';
import { ISugerenciaProducto, SugerenciaProducto, EstadoSugerencia } from 'app/shared/model/sugerencia-producto.model';

describe('Service Tests', () => {
  describe('SugerenciaProducto Service', () => {
    let injector: TestBed;
    let service: SugerenciaProductoService;
    let httpMock: HttpTestingController;
    let elemDefault: ISugerenciaProducto;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(SugerenciaProductoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SugerenciaProducto(
        0,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        false,
        false,
        false,
        currentDate,
        currentDate,
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        EstadoSugerencia.APROBADO
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaUltimaModificacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a SugerenciaProducto', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaUltimaModificacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
            fechaUltimaModificacion: currentDate
          },
          returnedFromService
        );
        service
          .create(new SugerenciaProducto(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a SugerenciaProducto', async () => {
        const returnedFromService = Object.assign(
          {
            nombreAlimento: 'BBBBBB',
            tamanoPorcion: 1,
            medidaCasera: 1,
            valorEnergetico: 1,
            caloriasGrasa: 1,
            grasaTotal: 1,
            grasaSaturada: 1,
            grasaInsaturada: 1,
            grasaTrans: 1,
            colesterol: 1,
            sodio: 1,
            carbohidrato: 1,
            fibraDietaria: 1,
            fibraInsoluble: 1,
            fibraSoluble: 1,
            azucares: 1,
            proteina: 1,
            vitaminaA: 1,
            vitaminaC: 1,
            calcio: 1,
            hierro: 1,
            gluten: true,
            azucar: true,
            integral: true,
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaUltimaModificacion: currentDate.format(DATE_TIME_FORMAT),
            estadoActivo: true,
            codigoDeBarras: 'BBBBBB',
            imagen: 'BBBBBB',
            observaciones: 'BBBBBB',
            estadoSugerencia: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
            fechaUltimaModificacion: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of SugerenciaProducto', async () => {
        const returnedFromService = Object.assign(
          {
            nombreAlimento: 'BBBBBB',
            tamanoPorcion: 1,
            medidaCasera: 1,
            valorEnergetico: 1,
            caloriasGrasa: 1,
            grasaTotal: 1,
            grasaSaturada: 1,
            grasaInsaturada: 1,
            grasaTrans: 1,
            colesterol: 1,
            sodio: 1,
            carbohidrato: 1,
            fibraDietaria: 1,
            fibraInsoluble: 1,
            fibraSoluble: 1,
            azucares: 1,
            proteina: 1,
            vitaminaA: 1,
            vitaminaC: 1,
            calcio: 1,
            hierro: 1,
            gluten: true,
            azucar: true,
            integral: true,
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaUltimaModificacion: currentDate.format(DATE_TIME_FORMAT),
            estadoActivo: true,
            codigoDeBarras: 'BBBBBB',
            imagen: 'BBBBBB',
            observaciones: 'BBBBBB',
            estadoSugerencia: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
            fechaUltimaModificacion: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SugerenciaProducto', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
